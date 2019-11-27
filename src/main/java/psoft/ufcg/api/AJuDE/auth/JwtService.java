package psoft.ufcg.api.AJuDE.auth;

import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import psoft.ufcg.api.AJuDE.exceptions.UnauthorizedException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;
import psoft.ufcg.api.AJuDE.usuario.UsuarioService;

@Api("Serviço JWT")
@Service
public class JwtService {

  @Autowired
  private UsuarioService usuarioService;

  @ApiOperation(value = "Valida usuário", notes = "Verifica se existe o usuário no sistema", response = boolean.class)
  public boolean validUsuario(String authorizationHeader) {
    String subject = getTokenSubject(authorizationHeader);

    return usuarioService.findByEmail(subject).isPresent();
  }

  public String getTokenSubject(String authorizationHeader) {
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
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

  @ApiOperation(value = "Permissão do usuário", notes = "Verifica se o usuário tem permissão", response = boolean.class)
  public boolean usuarioHasPermission(String authorizationHeader, String email) {
    String subject = getTokenSubject(authorizationHeader);

    Optional<Usuario> optUsuario = usuarioService.findByEmail(subject);
    return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
  }

  @ApiOperation(value = "Recupera usuário pelo token", notes = "Verifica qual o usuário referente ao token recebido", response = Usuario.class)
  public Optional<Usuario> getUsuarioByToken(String authorizationHeader) {
    if (!this.validUsuario(authorizationHeader))
      throw new UnauthorizedException("Usuário no token não é válido ou não existe");
    String subject = getTokenSubject(authorizationHeader);

    return usuarioService.findByEmail(subject);
  }
}