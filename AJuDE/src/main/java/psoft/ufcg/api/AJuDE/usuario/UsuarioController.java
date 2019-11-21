package psoft.ufcg.api.AJuDE.usuario;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import psoft.ufcg.api.AJuDE.exceptions.ResourceConflictException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping("/")
  public ResponseEntity<Usuario> create(@RequestBody Usuario user) {
	  Optional<Usuario> usuario = this.usuarioService.findByEmail(user.getEmail());
	  if(usuario.isPresent()) {
		  throw new ResourceConflictException("Usuário já cadastrado");
	  }
	  
	try {
	  return new ResponseEntity<Usuario>(this.usuarioService.save(user), HttpStatus.CREATED);
	
	} catch (ServletException e) {
	  return new ResponseEntity<Usuario>(HttpStatus.NOT_ACCEPTABLE);
	}
  }

  @GetMapping("/{email}")
  public ResponseEntity<Usuario> get(@PathVariable String email) {
    Optional<Usuario> usuario = this.usuarioService.findByEmail(email);
    if (!usuario.isPresent()){
      return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);

  }

}
