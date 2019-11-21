package psoft.ufcg.api.AJuDE.campanha.comentario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import psoft.ufcg.api.AJuDE.auth.JwtService;
import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.campanha.CampanhaService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;
import psoft.ufcg.api.AJuDE.exceptions.UnauthorizedException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

/**
 * 
 * @author Thayanne Luiza Victor Landim Sousa
 * @author Vítor Braga Diniz
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/campanhas/{campanhaIdURL}/comentarios")
public class ComentarioController {

	@Autowired
	ComentarioService comentarioService;

	@Autowired
	CampanhaService campanhaService;
	
	@Autowired
	JwtService jwtService;

	@PostMapping("/")
	public ResponseEntity<Comentario> create(@PathVariable String campanhaIdURL, @RequestBody Comentario comentario, 
			@RequestHeader("Authorization") String header) {
		
		comentario = validateComentario(comentario, campanhaIdURL, header);
		return new ResponseEntity<Comentario>(this.comentarioService.save(comentario), HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<Comentario>> getAll(@PathVariable String campanhaIdURL) {
		List<Comentario> comentariosPage = this.comentarioService.getAllByCampanhaId(campanhaIdURL);

		return new ResponseEntity<List<Comentario>>(comentariosPage, HttpStatus.OK);
	}

	@GetMapping("/{comentarioId}")
	public ResponseEntity<Comentario> get(@PathVariable int comentarioId) {
		Optional<Comentario> comentario = this.comentarioService.findById(comentarioId);
		if (!comentario.isPresent()){
			throw new ResourceNotFoundException("Comentario não encontrado.");
		}
		return new ResponseEntity<Comentario>(comentario.get(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{comentarioId}")
	public ResponseEntity<Comentario> delete(@PathVariable int comentarioId, @RequestHeader("Authorization") String header) {
		Optional<Comentario> comentario = this.comentarioService.deleteById(comentarioId);
		if (!comentario.isPresent()){
			throw new ResourceNotFoundException("Comentario não encontrado.");
		}
		if(jwtService.usuarioHasPermission(header, comentario.get().getUsuario().getEmail()))
			throw new UnauthorizedException("Usuário não tem permissão para apagar esse comentário.");
		return new ResponseEntity<Comentario>(comentario.get(), HttpStatus.OK);
	}
	
	@PostMapping("/{comentarioId}")
	public ResponseEntity<Comentario> create(@PathVariable String campanhaIdURL, @PathVariable int comentarioId, 
			@RequestBody Comentario comentario, @RequestHeader("Authorization") String header) {
		if (!this.comentarioService.findById(comentarioId).isPresent()){
			throw new ResourceNotFoundException("Comentario não encontrado.");
		}
		comentario = validateComentario(comentario, campanhaIdURL, header);		
		return new ResponseEntity<Comentario>(this.comentarioService.save(comentario), HttpStatus.OK);
	}

	private Comentario validateComentario(Comentario comentario, String campanhaIdURL, String authorizationHeader) {
		Optional<Usuario> usuario = jwtService.getUsuarioByToken(authorizationHeader);
		if(!usuario.isPresent())
			throw new UnauthorizedException("Você precisa estar autenticado para criar uma nova campanha.");
		
		Campanha campanha = getCampanhaByIdURL(campanhaIdURL);
		comentario.setCampanha(campanha);
		comentario.setUsuario(usuario.get());
		return comentario;
	}
	/**
	 * Retorna um objeto campanha a partir de um identificador de URL. Caso não existe nenhuma campanha com esse identificador, 
	 * retorna um erro HTTP 404. 
	 * @param identificadorURL
	 * @return Campanha campanha com o Identificador de URL passado
	 */
	private Campanha getCampanhaByIdURL(String identificadorURL) {
		Campanha campanha = this.campanhaService.findByIdURL(identificadorURL);

		if(campanha.isEmpty()) 
			throw new ResourceNotFoundException("Campanha não existe.");

		return campanha;
	}
}