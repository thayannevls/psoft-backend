package psoft.ufcg.api.AJuDE.campanha.like;

import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(value="likes")
@RestController
@RequestMapping("/campanhas/{campanhaIdURL}/likes")
public class LikeController {
	
	@Autowired
	private CampanhaService campanhaService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "Recupera a quantidade de likes de uma campanha")
	@GetMapping("/")
	public ResponseEntity<CampanhaLikesTotalResponse> getQuantity(@PathVariable String campanhaIdURL, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = this.jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent()) {
			throw new ResourceNotFoundException("Precisa estar autenticado para fazer ação de like.");
		}
		Optional<Campanha> campanha = this.campanhaService.findByIdURL(campanhaIdURL);
		if(!campanha.isPresent()) {
			throw new ResourceNotFoundException("Campanha não encontrada.");
		}
		Like like = new Like(campanha.get(), usuario.get());
		return new ResponseEntity<CampanhaLikesTotalResponse>(new CampanhaLikesTotalResponse(this.likeService.getTotalLikes(like)), HttpStatus.OK);
	}

	@ApiOperation(value = "Adiciona ou remove like de um usuário para uma campanha específica")
	@PostMapping("/")
	public ResponseEntity<LikeResponseDTO> likeOrDeslike(@PathVariable String campanhaIdURL, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = this.jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent()) {
			throw new ResourceNotFoundException("Precisa estar autenticado para fazer ação de like.");
		}
		Optional<Campanha> optCampanha = this.campanhaService.findByIdURL(campanhaIdURL);
		if(!optCampanha.isPresent()) {
			throw new ResourceNotFoundException("Campanha não encontrada.");
		}
		Campanha campanha = optCampanha.get();
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

	@ApiOperation(value = "Recupera like de um usuário a uma campanha")
	@GetMapping("/usuario")
	public ResponseEntity<LikeResponseDTO> getLikeByUser(@PathVariable String campanhaIdURL, @RequestHeader("Authorization") String header) {
		Optional<Usuario> usuario = this.jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent()) {
			throw new ResourceNotFoundException("Precisa estar autenticado para fazer ação de like.");
		}
		Optional<Campanha> campanha = this.campanhaService.findByIdURL(campanhaIdURL);
		if(!campanha.isPresent()) {
			throw new ResourceNotFoundException("Campanha não encontrada.");
		}
		
		Like like = new Like(campanha.get(), usuario.get());
		if(this.likeService.findByCampanhaAndUsuario(campanha.get(), usuario.get()).isPresent()) {
			LikeResponseDTO response = LikeResponseDTO.objToDTO(like, "add");
			return new ResponseEntity<LikeResponseDTO>(response, HttpStatus.OK);
		}
		
		throw new ResourceNotFoundException("Usuário não possui like nessa campanha.");
	}
	

	@Api(value = "Likes responses")
	public class CampanhaLikesTotalResponse {

	    public Long total;

	    public CampanhaLikesTotalResponse(Long s) { 
	       this.total = s;
	    }
	}
}
