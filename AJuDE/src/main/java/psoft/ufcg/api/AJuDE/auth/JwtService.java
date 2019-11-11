package psoft.ufcg.api.AJuDE.auth;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;
import psoft.ufcg.api.AJuDE.usuario.UsuarioService;


@Service
public class JwtService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	public boolean validUsuario(String authorizationHeader) throws ServletException {
		String subject = getTokenSubject(authorizationHeader);

		return usuarioService.getUsuario(subject).isPresent();
	}
	
	public boolean usuarioHasPermission(String authorizationHeader, String email) throws ServletException {
		String subject = getTokenSubject(authorizationHeader);

		Optional<Usuario> optUsuario = usuarioService.getUsuario(subject);
		return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
	}
	
	private String getTokenSubject(String authorizationHeader) throws ServletException {
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new ServletException("Token inválido");
		}
		
		String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);
		
		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey("segredo").parseClaimsJws(token).getBody().getSubject();
		} catch (SignatureException e) {
			throw new ServletException("Token inválido ou expirado!");
		}
		return subject;
	}
}