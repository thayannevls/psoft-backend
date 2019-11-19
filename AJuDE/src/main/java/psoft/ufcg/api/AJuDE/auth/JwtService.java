package psoft.ufcg.api.AJuDE.auth;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import psoft.ufcg.api.AJuDE.exceptions.UnauthorizedException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;
import psoft.ufcg.api.AJuDE.usuario.UsuarioService;


@Service
public class JwtService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	public boolean validUsuario(String authorizationHeader) {
		String subject = getTokenSubject(authorizationHeader);

		return usuarioService.findByEmail(subject).isPresent();
	}
	
	public boolean usuarioHasPermission(String authorizationHeader, String email)  {
		String subject = getTokenSubject(authorizationHeader);

		Optional<Usuario> optUsuario = usuarioService.findByEmail(subject);
		return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
	}
	
	public String getTokenSubject(String authorizationHeader) {
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new UnauthorizedException("Token inválido");
		}
		
		String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);
		
		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey("segredo").parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new UnauthorizedException("Token inválido ou expirado!");
		}
		return subject;
	}
	
	public Optional<Usuario> getUsuarioByToken(String authorizationHeader)  {
		if(!this.validUsuario(authorizationHeader))
			throw new UnauthorizedException("Usuário no token não é válido ou não existe");
		String subject = getTokenSubject(authorizationHeader);

		return usuarioService.findByEmail(subject);
	}
}