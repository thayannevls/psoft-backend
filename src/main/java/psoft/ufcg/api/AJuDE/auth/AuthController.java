package psoft.ufcg.api.AJuDE.auth;

import java.util.Date;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Api(value = "Campanhas")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final String TOKEN_KEY = "segredo";

	@Value("${security.jwt.token.expire-length}")
	private long tokenExpirationLength;
	
	@Autowired
	private UsuarioService usuarioService;

	@ApiOperation(value = "Autentica um usuário")
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
				.setExpiration(new Date(System.currentTimeMillis() + tokenExpirationLength)).compact();

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
