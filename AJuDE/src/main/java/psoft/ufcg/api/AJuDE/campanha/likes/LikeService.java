package psoft.ufcg.api.AJuDE.campanha.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
	
	@Autowired
	private LikeRepository<Like, Integer> likeDAO;

	public Like save(Like like) {
		return this.likeDAO.save(like);
	}

	public long getTotalLikes(Like like) {
		return this.likeDAO.count();
	}

}
