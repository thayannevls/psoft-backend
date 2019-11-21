package psoft.ufcg.api.AJuDE.campanha.likes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<LikeQuantityResponse> getQuantity(@PathVariable String campanhaIdURL, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = this.jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent()) {
			throw new ResourceNotFoundException("Precisa estar autenticado para fazer ação de like.");
		}
		Campanha campanha = this.campanhaService.findByIdURL(campanhaIdURL);
		if(campanha == null) {
			throw new ResourceNotFoundException("Campanha não encontrada..");
		}
		Like like = new Like(campanha, usuario.get());
		return new ResponseEntity<LikeQuantityResponse>(new LikeQuantityResponse(this.likeService.getTotalLikes(like)), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Like> create(@PathVariable String campanhaIdURL, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = this.jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent()) {
			throw new ResourceNotFoundException("Precisa estar autenticado para fazer ação de like.");
		}
		Campanha campanha = this.campanhaService.findByIdURL(campanhaIdURL);
		if(campanha == null) {
			throw new ResourceNotFoundException("Campanha não encontrada..");
		}
		
		Like like = new Like(campanha, usuario.get());
		this.likeService.save(like);
		return new ResponseEntity<Like>(like, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<Like> delete(@PathVariable String campanhaIdURL, @RequestHeader("Authorization") String header) {
		
		
		return new ResponseEntity<Like>(null);
	}
	
	public class LikeQuantityResponse {

	    public Long quantity;

	    public LikeQuantityResponse(Long s) { 
	       this.quantity = s;
	    }

	    // get/set omitted...
	}
}
