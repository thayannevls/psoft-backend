package psoft.ufcg.api.AJuDE.campanha;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import psoft.ufcg.api.AJuDE.auth.AuthController;
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

	@ApiOperation(value = "Recupera uma campanha", notes = "Recupera uma campanha através do seu identificador de URL.", position = 0)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Campanha encontrada", response = Campanha.class),
			@ApiResponse(code = 401, message = "Senha fornecida incorreta"),
			@ApiResponse(code = 404, message = "Campanha não encontrada")
	})
	@GetMapping("/{identificadorURL}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CampanhaResponseDTO> get(@PathVariable String identificadorURL) {
		Optional<Campanha> campanha = this.campanhaService.findByIdURL(identificadorURL);
		if(!campanha.isPresent())
			throw new ResourceNotFoundException("Campanha não encontrada.");
		
		return new ResponseEntity<CampanhaResponseDTO>(CampanhaResponseDTO.objToDTO(campanha.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Recupera campanhas", notes = "Recupera todas as campanhas do sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lista de campanhas retornada", response = List.class)
	})
	@GetMapping("/")
	public ResponseEntity<List<Campanha>> get() {
		return new ResponseEntity<List<Campanha>>(this.campanhaService.findAll(), HttpStatus.OK);
	}


	@ApiOperation(value = "Monta ranking de campanhas", notes = "Monta e envia o ranking de todas as campanhas")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ranking criado", response = List.class)
	})
	@GetMapping("/rank")
	public ResponseEntity<List<Campanha>> getRank(
			@RequestParam(name = "sort", defaultValue = "meta") String sortMethod) {
		return new ResponseEntity<List<Campanha>>(this.campanhaService.getRank(sortMethod), HttpStatus.OK);
	}

	@ApiOperation(value = "Cria campanha", notes = "Cria uma nova campanha")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ranking criado", response = List.class),
			@ApiResponse(code = 401, message = "Usuário não autenticado"),
			@ApiResponse(code = 409, message = "Campanha já cadastrada")
	})
	@PostMapping("/")
	public ResponseEntity<CampanhaResponseDTO> create(@RequestBody CampanhaDTO campanhaDTO, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = jwtService.getUsuarioByToken(header);
		Campanha campanha = campanhaDTO.get();
		if(!usuario.isPresent())
			throw new UnauthorizedException("Você precisa estar autenticado para criar uma nova campanha.");
		if(this.campanhaService.findByIdURL(campanha.getIdentificadorURL()).isPresent())
			throw new ResourceConflictException("Campanha já cadastrada com esse identificador de URL."); 
		campanha.setDono(usuario.get());

		return new ResponseEntity<CampanhaResponseDTO>(
				CampanhaResponseDTO.objToDTO(this.campanhaService.save(campanha)), 
				HttpStatus.CREATED);
	}

	@ApiOperation(value = "Busca campanha", notes = "Busca uma campanha no sistema a partir de uma substring")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Campanha encontrada", response = List.class)
	})
	@GetMapping("/search")
	public ResponseEntity<List<Campanha>> search(@RequestParam(name = "substring") String substring, 
			@RequestParam(name = "status", required = false) List<String> status) {
		if(status == null) {
			status = Arrays.asList("ATIVA");
		}
		return new ResponseEntity<List<Campanha>>(this.campanhaService.findBySubstring(substring, status), HttpStatus.OK);
	}


	@ApiOperation(value = "Atualiza campanha", notes = "Atualiza as informações de uma campanha")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ranking criado", response = List.class),
			@ApiResponse(code = 401, message = "Usuário não autenticado ou sem permissão de acesso"),
			@ApiResponse(code = 404, message = "Nehuma campanha encontrada")
	})
	@PutMapping("/{identificadorURL}")
	public ResponseEntity<Campanha> updateCampanha(@PathVariable String identificadorURL, @RequestBody CampanhaDTO campanhaDTO, @RequestHeader("Authorization") String header){
		Optional<Campanha> optCampanha = this.campanhaService.findByIdURL(identificadorURL);
		Optional<Usuario> usuario = jwtService.getUsuarioByToken(header);

		if(!optCampanha.isPresent()) {
			throw new ResourceNotFoundException("Campanha não encontrada.");
		}
		Campanha campanha = optCampanha.get();

		if(!usuario.isPresent())
			throw new UnauthorizedException("Usuário precisa estar autenticado para atualizar uma nova campanha.");

		if(!usuario.get().getEmail().equals(campanha.getDono().getEmail()))
			throw new UnauthorizedException("Usuárion não possui permissão para atualizar essa campanha.");

		Campanha mudancas = campanhaDTO.get();
		return new ResponseEntity<Campanha>(this.campanhaService.update(campanha, mudancas), HttpStatus.OK);
	}
}
