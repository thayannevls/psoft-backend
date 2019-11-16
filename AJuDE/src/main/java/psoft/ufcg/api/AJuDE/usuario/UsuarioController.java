package psoft.ufcg.api.AJuDE.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import psoft.ufcg.api.AJuDE.auth.JwtService;

import javax.servlet.ServletException;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;
  @Autowired
  private JwtService jwtService;


  @PostMapping("/")
  public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario user) {
    try {
      return new ResponseEntity<Usuario>(this.usuarioService.save(user), HttpStatus.OK);

    } catch (ServletException e) {
      return new ResponseEntity<Usuario>(HttpStatus.NOT_ACCEPTABLE);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Usuario> getUsuario(@RequestBody String email) {
    Optional<Usuario> usuario = this.usuarioService.getUsuario(email);
    if (!usuario.isPresent()){
      return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);

  }



}
