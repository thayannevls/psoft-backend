package psoft.ufcg.api.AJuDE.campanha;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import psoft.ufcg.api.AJuDE.auth.JwtService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceConflictException;
import psoft.ufcg.api.AJuDE.exceptions.UnauthorizedException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@Api(value = "Campanhas")
@RestController
@RequestMapping("/campanhas")
public class CampanhaController {

	@Autowired
	CampanhaService campanhaService;

	@Autowired
	JwtService jwtService;
	
	@ApiOperation(value = "Recupera uma campanha através do seu identificador de URL.")
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
	
	@GetMapping("/rank")
	public ResponseEntity<List<Campanha>> getRank(
			@RequestParam(name = "sort", defaultValue = "meta") String sortMethod) {
		
		return new ResponseEntity<List<Campanha>>(this.campanhaService.getRank(sortMethod), HttpStatus.OK);
	}
 	@PostMapping("/")
	public ResponseEntity<CampanhaResponseDTO> create(@RequestBody CampanhaDTO campanhaDTO, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = jwtService.getUsuarioByToken(header);
		Campanha campanha = campanhaDTO.get();
		if(!usuario.isPresent())
			throw new UnauthorizedException("Você precisa estar autenticado para criar uma nova campanha.");
		if(this.campanhaService.findByIdURL(campanha.getIdentificadorURL()) != null)
			throw new ResourceConflictException("Campanha já cadastrada com esse identificador de URL."); 
		campanha.setDono(usuario.get());

		return new ResponseEntity<CampanhaResponseDTO>(
				CampanhaResponseDTO.objToDTO(this.campanhaService.save(campanha)), 
				HttpStatus.CREATED);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Campanha>> search(@RequestParam(name = "substring") String substring, 
			@RequestParam(name = "status", required = false) List<String> status) {
		if(status == null) {
			status = Arrays.asList("ATIVA");
		}
		return new ResponseEntity<List<Campanha>>(this.campanhaService.findBySubstring(substring, status), HttpStatus.OK);
	}
}
