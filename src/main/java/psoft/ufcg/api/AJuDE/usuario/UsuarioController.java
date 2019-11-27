package psoft.ufcg.api.AJuDE.usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.campanha.CampanhaResponseDTO;
import psoft.ufcg.api.AJuDE.campanha.CampanhaService;
import psoft.ufcg.api.AJuDE.doacao.Doacao;
import psoft.ufcg.api.AJuDE.doacao.DoacaoResponseDTO;
import psoft.ufcg.api.AJuDE.doacao.DoacaoService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceConflictException;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private DoacaoService doacaoService;
	
	@Autowired
	private CampanhaService campanhaService;

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

	@GetMapping("/{email:.+}")
	public ResponseEntity<UsuarioResponseDTO> get(@PathVariable String email) {
		Optional<Usuario> usuario = this.usuarioService.findByEmail(email);
		if (!usuario.isPresent()){
			throw new ResourceNotFoundException("Usuário não encontrado");
		}

		return new ResponseEntity<UsuarioResponseDTO>(UsuarioResponseDTO.objToDTO(usuario.get()), HttpStatus.OK);
	}

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
	
	@GetMapping("/{email}/participacoes")
	public ResponseEntity<List<CampanhaResponseDTO>> getParticipacoes(@PathVariable String email) {
		Optional<Usuario> usuario = this.usuarioService.findByEmail(email);
		if (!usuario.isPresent()){
			throw new ResourceNotFoundException("Usuário não encontrado");
		}
		
		List<CampanhaResponseDTO> participacoes = this.usuarioService.getParticipacoes(email).stream()
																		.map(c -> CampanhaResponseDTO.objToDTO(c))
																		.collect(Collectors.toList());
		
		return new ResponseEntity<List<CampanhaResponseDTO>>(participacoes, HttpStatus.OK);
	}
}
