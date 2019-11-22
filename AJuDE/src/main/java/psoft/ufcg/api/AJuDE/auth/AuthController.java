package psoft.ufcg.api.AJuDE.auth;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;
import psoft.ufcg.api.AJuDE.exceptions.UnauthorizedException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;
import psoft.ufcg.api.AJuDE.usuario.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final String TOKEN_KEY = "segredo";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/")
	public LoginResponseDTO authenticate(@RequestBody Usuario usuario) {
		Optional<Usuario> authUsuario = usuarioService.findByEmail(usuario.getEmail());
		
		if(!authUsuario.isPresent()) {
			throw new ResourceNotFoundException("Usuario nao encontrado!");
		}
		
		if(!authUsuario.get().getSenha().equals(usuario.getSenha())) {
			throw new UnauthorizedException("Senha incorreta!");
		}
		
		String token = Jwts.builder().setSubject(authUsuario.get().getEmail()).signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000)).compact();

		return new LoginResponseDTO(token);
	}
	
	private class LoginResponseDTO {
		@SuppressWarnings("unused")
		public String token;
		
		public LoginResponseDTO(String token) {
			this.token = token;
		}
	}
}
