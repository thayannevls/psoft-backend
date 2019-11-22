package psoft.ufcg.api.AJuDE.campanha.likes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.exceptions.ResourceConflictException;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@Service
public class LikeService {
	
	@Autowired
	private LikeRepository<Like, Integer> likeDAO;
	
	public Optional<Like> findByCampanhaAndUsuario(Campanha campanha, Usuario usuario) {
		Optional<Like> like = this.likeDAO.findByCampanhaIdentificadorURLAndUsuarioEmail(campanha.getIdentificadorURL(), 
																						usuario.getEmail());
		
		return like;
	}
	
	public Optional<Like> findByCampanhaAndUsuario(String campanhaIdURL, String usuarioEmail) {
		Optional<Like> like = this.likeDAO.findByCampanhaIdentificadorURLAndUsuarioEmail(campanhaIdURL, usuarioEmail);
		
		return like;
	}

	public Like save(Campanha campanha, Usuario usuario) {
		Optional<Like> like = this.findByCampanhaAndUsuario(campanha, usuario);
		
		if(like.isPresent())
			throw new ResourceConflictException("Usuario já deu like nessa campanha.");
		
		return this.likeDAO.save(new Like(campanha, usuario));
	}

	public Like delete(Campanha campanha, Usuario usuario) {
		Optional<Like> like = this.findByCampanhaAndUsuario(campanha, usuario);
		
		if(!like.isPresent())
			throw new ResourceNotFoundException("Usuario não deu like nessa campanha.");
		
		this.likeDAO.deleteById(like.get().getId());
		
		return like.get();
	}

	public long getTotalLikes(Like like) {
		return this.likeDAO.count();
	}

}
