package psoft.ufcg.api.AJuDE.auth;

import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import psoft.ufcg.api.AJuDE.usuario.Usuario;
import psoft.ufcg.api.AJuDE.usuario.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final String TOKEN_KEY = "segredo";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/")
	public LoginResponse authenticate(@RequestBody Usuario usuario) throws ServletException {
		Optional<Usuario> authUsuario = usuarioService.getUsuario(usuario.getEmail());
		
		if(!authUsuario.isPresent()) {
			throw new ServletException("Usuario nao encontrado!");
		}
		
		if(!authUsuario.get().getSenha().equals(usuario.getSenha())) {
			throw new ServletException("Senha incorreta!");
		}
		
		String token = Jwts.builder().setSubject(authUsuario.get().getEmail()).signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000)).compact();

		return new LoginResponse(token);
	}
	
	private class LoginResponse {
		public String token;
		
		public LoginResponse(String token) {
			this.token = token;
		}
	}
}
