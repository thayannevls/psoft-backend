package psoft.ufcg.api.AJuDE.usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.campanha.CampanhaResponseDTO;
import psoft.ufcg.api.AJuDE.campanha.CampanhaService;
import psoft.ufcg.api.AJuDE.doacao.Doacao;
import psoft.ufcg.api.AJuDE.doacao.DoacaoResponseDTO;
import psoft.ufcg.api.AJuDE.doacao.DoacaoService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceConflictException;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;

@Api(value = "Usuários")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private DoacaoService doacaoService;
	
	@Autowired
	private CampanhaService campanhaService;


	@ApiOperation(value = "Cadastra usuário", notes = "Cadastra usuário ao sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário cadastrado", response = Usuario.class),
			@ApiResponse(code = 406, message = "Usuário já cadastrado")
	})
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


	@ApiOperation(value = "Recupera usuário", notes = "Recupera um usuário do sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário recuperado", response = Usuario.class),
			@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
	@GetMapping("/{email:.+}")
	public ResponseEntity<UsuarioResponseDTO> get(@PathVariable String email) {
		Optional<Usuario> usuario = this.usuarioService.findByEmail(email);
		if (!usuario.isPresent()){
			throw new ResourceNotFoundException("Usuário não encontrado");
		}

		return new ResponseEntity<UsuarioResponseDTO>(UsuarioResponseDTO.objToDTO(usuario.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Recupera doações", notes = "Recupera doações feitas por um usuário do sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário recuperado", response = Usuario.class),
			@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
	@GetMapping("/{email}/doacoes")
	public ResponseEntity<List<DoacaoResponseDTO>> getDoacoes(@PathVariable String email) {
		Optional<Usuario> usuario = this.usuarioService.findByEmail(email);
		if (!usuario.isPresent()){
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
		
		List<Doacao> doacoes = this.doacaoService.getByUsuarioEmail(email);
		List<DoacaoResponseDTO> response = doacoes.stream()
												.map(d -> DoacaoResponseDTO.objToDTO(d))
												.collect(Collectors.toList());
		
		return new ResponseEntity<List<DoacaoResponseDTO>>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Recupera campanhas", notes = "Recupera as campanhas de um usuário do sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário recuperado", response = Usuario.class),
			@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
	@GetMapping("/{email}/campanhas")
	public ResponseEntity<List<CampanhaResponseDTO>> getCampanhas(@PathVariable String email) {
		Optional<Usuario> usuario = this.usuarioService.findByEmail(email);
		if (!usuario.isPresent()){
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
		
		List<Campanha> campanhas = this.campanhaService.getByUsuarioEmail(email);
		List<CampanhaResponseDTO> response = campanhas.stream()
												.map(c -> CampanhaResponseDTO.objToDTO(c))
												.collect(Collectors.toList());
		
		return new ResponseEntity<List<CampanhaResponseDTO>>(response, HttpStatus.OK);
	}


	@ApiOperation(value = "Recupera campanhas que participou", notes = "Recupera as campanhas que um usuário criou ou ajudou")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário recuperado", response = Usuario.class),
			@ApiResponse(code = 404, message = "Usuário não encontrado")
	})
	@GetMapping("/{email}/participacoes")
	public ResponseEntity<List<CampanhaResponseDTO>> getParticipacoes(@PathVariable String email, 
			@RequestParam(name = "substring", required = false) String substring) {

		Optional<Usuario> usuario = this.usuarioService.findByEmail(email);
		if (!usuario.isPresent()){
			throw new ResourceNotFoundException("Usuário não encontrado");
		}

		List<CampanhaResponseDTO> participacoes = this.usuarioService.getParticipacoes(email, substring).stream()
																		.map(c -> CampanhaResponseDTO.objToDTO(c))
																		.collect(Collectors.toList());
		
		return new ResponseEntity<List<CampanhaResponseDTO>>(participacoes, HttpStatus.OK);
	}
}
