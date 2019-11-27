package psoft.ufcg.api.AJuDE.campanha.like;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import psoft.ufcg.api.AJuDE.auth.JwtService;
import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.campanha.CampanhaService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@RestController
@RequestMapping("/campanhas/{campanhaIdURL}/likes")
public class LikeController {
	
	@Autowired
	private CampanhaService campanhaService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/")
	public ResponseEntity<CampanhaLikesTotalResponse> getQuantity(@PathVariable String campanhaIdURL, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = this.jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent()) {
			throw new ResourceNotFoundException("Precisa estar autenticado para fazer ação de like.");
		}
		Campanha campanha = this.campanhaService.findByIdURL(campanhaIdURL);
		if(campanha == null) {
			throw new ResourceNotFoundException("Campanha não encontrada.");
		}
		Like like = new Like(campanha, usuario.get());
		return new ResponseEntity<CampanhaLikesTotalResponse>(new CampanhaLikesTotalResponse(this.likeService.getTotalLikes(like)), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<LikeResponseDTO> likeOrDeslike(@PathVariable String campanhaIdURL, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = this.jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent()) {
			throw new ResourceNotFoundException("Precisa estar autenticado para fazer ação de like.");
		}
		Campanha campanha = this.campanhaService.findByIdURL(campanhaIdURL);
		if(campanha == null) {
			throw new ResourceNotFoundException("Campanha não encontrada.");
		}
		Like like = new Like(campanha, usuario.get());
		if(this.likeService.findByCampanhaAndUsuario(campanha, usuario.get()).isPresent()) {
			this.likeService.delete(campanha, usuario.get());
			campanha.removeLike();
			this.campanhaService.save(campanha);
			LikeResponseDTO response = LikeResponseDTO.objToDTO(like, "remove");
			return new ResponseEntity<LikeResponseDTO>(response, HttpStatus.OK);
		}
		
		campanha.addLike();
		this.campanhaService.save(campanha);
		this.likeService.save(campanha, usuario.get());
		LikeResponseDTO response = LikeResponseDTO.objToDTO(like, "add");
		return new ResponseEntity<LikeResponseDTO>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<LikeResponseDTO> getLikeByUser(@PathVariable String campanhaIdURL, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = this.jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent()) {
			throw new ResourceNotFoundException("Precisa estar autenticado para fazer ação de like.");
		}
		Campanha campanha = this.campanhaService.findByIdURL(campanhaIdURL);
		if(campanha == null) {
			throw new ResourceNotFoundException("Campanha não encontrada.");
		}
		
		Like like = new Like(campanha, usuario.get());
		if(this.likeService.findByCampanhaAndUsuario(campanha, usuario.get()).isPresent()) {
			LikeResponseDTO response = LikeResponseDTO.objToDTO(like, "add");
			return new ResponseEntity<LikeResponseDTO>(response, HttpStatus.OK);
		}
		
		throw new ResourceNotFoundException("Usuário não possui like nessa campanha.");
	}
	
	
	public class CampanhaLikesTotalResponse {

	    public Long total;

	    public CampanhaLikesTotalResponse(Long s) { 
	       this.total = s;
	    }
	}
}
