package psoft.ufcg.api.AJuDE.auth;

import java.util.Date;
import java.util.Optional;

import io.swagger.annotations.*;
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

@Api("Autenticação")
@RestController
@RequestMapping("/auth")
public class AuthController {


  @ApiModelProperty(value = "Chave de verificação do token", example = "segredo", hidden = true)
  private final String TOKEN_KEY = "segredo";

  @ApiModelProperty(value = "Tempo de expiração do token", example = "200000000", hidden = true)
  @Value("${security.jwt.token.expire-length}")
  private long tokenExpirationLength;


  @Autowired
  private UsuarioService usuarioService;

  @ApiOperation(value = "Autentica um usuário", notes = "Autentica um usuário a partir do token enviado por ele", response = LoginResponseDTO.class, position = 0)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Retorna o JWT token", response = LoginResponseDTO.class),
          @ApiResponse(code = 401, message = "Senha fornecida incorreta"),
          @ApiResponse(code = 404, message = "Usuário não encontrado")
  })
  @PostMapping("/")
  public LoginResponseDTO authenticate(@RequestBody Usuario usuario) {
    Optional<Usuario> authUsuario = usuarioService.findByEmail(usuario.getEmail());

    if (!authUsuario.isPresent()) {
      throw new ResourceNotFoundException("Usuario nao encontrado!");
    }

    if (!authUsuario.get().getSenha().equals(usuario.getSenha())) {
      throw new UnauthorizedException("Senha incorreta!");
    }

    String token = Jwts.builder().setSubject(authUsuario.get().getEmail()).signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
            .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationLength)).compact();

    return new LoginResponseDTO(token);
  }

  @ApiModel(value = "Resposta do login contendo token")
  private class LoginResponseDTO {
    @SuppressWarnings("unused")
    @ApiModelProperty(value = "JWT token", example = "dWUzOEBsaXZlLmNvbSIsImV4cCI6MTU2MTA4NzM3M30.NEJoipQvJ", hidden = true)
    public String token;

    public LoginResponseDTO(String token) {
      this.token = token;
    }
  }
}
