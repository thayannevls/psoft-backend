package psoft.ufcg.api.AJuDE.campanha;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import psoft.ufcg.api.AJuDE.auth.JwtService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceConflictException;
import psoft.ufcg.api.AJuDE.exceptions.UnauthorizedException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@RestController
@RequestMapping("/campanhas")
public class CampanhaController {

	@Autowired
	CampanhaService campanhaService;

	@Autowired
	JwtService jwtService;

	@GetMapping("/{identificadorURL}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Campanha> get(@PathVariable String identificadorURL) {
		Campanha campanha = this.campanhaService.findByIdURL(identificadorURL);		
		return new ResponseEntity<Campanha>(campanha, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Campanha>> get() {
		return new ResponseEntity<List<Campanha>>(this.campanhaService.findAll(), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Campanha> create(@RequestBody Campanha campanha, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent())
			throw new UnauthorizedException("Você precisa estar autenticado para criar uma nova campanha.");
		
		if(this.campanhaService.findByIdURL(campanha.getIdentificadorURL()).isEmpty())
			throw new ResourceConflictException("Campanha já cadastrada com esse identificador de url"); 

		campanha.setDono(usuario.get());
		return new ResponseEntity<Campanha>(this.campanhaService.save(campanha), HttpStatus.CREATED);
	}

}
