package psoft.ufcg.api.AJuDE.campanha;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampanhaRepository<T, ID extends Serializable> extends JpaRepository<Campanha, Integer> {

	Optional<Campanha> findByIdentificadorURL(String identificadorURL);

	List<Campanha> findByNomeContainsIgnoreCase(String substring);
	
	List<Campanha> findByDonoEmailAndNomeContainsIgnoreCase(String email, String substring);
	
	List<Campanha> findByDonoEmailAndDescricaoContainsIgnoreCase(String email, String substring);

	List<Campanha> findByDonoEmail(String email);
	
	List<Campanha> findByOrderByLikesDesc();
}