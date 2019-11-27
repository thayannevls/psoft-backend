package psoft.ufcg.api.AJuDE.campanha;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import psoft.ufcg.api.AJuDE.auth.JwtService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceConflictException;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;
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
	@ApiOperation(value = "Recupera todas as campanhas")
	@GetMapping("/")
	public ResponseEntity<List<Campanha>> get() {
			return new ResponseEntity<List<Campanha>>(this.campanhaService.findAll(), HttpStatus.OK);
	}

	@ApiOperation(value = "Monta e envia o ranking de todas as campanhas")
	@GetMapping("/rank")
	public ResponseEntity<List<Campanha>> getRank(
			@RequestParam(name = "sort", defaultValue = "meta") String sortMethod) {
		return new ResponseEntity<List<Campanha>>(this.campanhaService.getRank(sortMethod), HttpStatus.OK);
	}

	@ApiOperation(value = "Cria uma nova campanha")
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

	@ApiOperation(value = "Busca uma campanha no sistema")
	@GetMapping("/search")
	public ResponseEntity<List<Campanha>> search(@RequestParam(name = "substring") String substring, 
			@RequestParam(name = "status", required = false) List<String> status) {
		if(status == null) {
			status = Arrays.asList("ATIVA");
		}
		return new ResponseEntity<List<Campanha>>(this.campanhaService.findBySubstring(substring, status), HttpStatus.OK);
	}


	@ApiOperation(value = "Atualiza as informações de uma campanha")
	@PutMapping("/{identificadorURL}")
	public ResponseEntity<Campanha> updateCampanha(@PathVariable String identificadorURL, @RequestBody CampanhaDTO campanhaDTO, @RequestHeader("Authorization") String header){
		Campanha campanha = this.campanhaService.findByIdURL(identificadorURL);
		Optional<Usuario> usuario = jwtService.getUsuarioByToken(header);
		if(campanha == null) {
			throw new ResourceNotFoundException("Campanha não encontrada.");
		}
		
		if(!usuario.isPresent())
			throw new UnauthorizedException("Usuário precisa estar autenticado para atualizar uma nova campanha.");
		
		if(!usuario.get().getEmail().equals(campanha.getDono().getEmail()))
			throw new UnauthorizedException("Usuárion não possui permissão para atualizar essa campanha.");
		
		Campanha mudancas = campanhaDTO.get();
		return new ResponseEntity<Campanha>(this.campanhaService.update(campanha, mudancas), HttpStatus.OK);
	}
}
