package psoft.ufcg.api.AJuDE.campanha.like;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository<T, ID extends Serializable> extends JpaRepository<Like, Long> {
	
	Optional<Like> findByCampanhaIdentificadorURLAndUsuarioEmail(String campanhaIdURL, String usuarioEmail);
}
