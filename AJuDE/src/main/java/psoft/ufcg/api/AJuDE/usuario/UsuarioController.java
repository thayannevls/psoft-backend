package psoft.ufcg.api.AJuDE.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import psoft.ufcg.api.AJuDE.auth.JwtService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private JwtService jwtService;


	@PostMapping("/")
	public ResponseEntity<Usuario> addUser(@RequestBody Usuario user){
		return new ResponseEntity<Usuario> (this.usuarioService.save(user), HttpStatus.OK);
	}
}
