package psoft.ufcg.api.AJuDE.campanha;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CampanhaRepository<T, ID extends Serializable> extends JpaRepository<Campanha, Integer> {

	Campanha findByIdentificadorURL(String identificadorURL);

	List<Campanha> findByNomeContainsIgnoreCase(String substring);
	
	List<Campanha> findByDonoEmailAndNomeContainsIgnoreCaseOrDescricaoContainsIgnoreCase(String email, String substringNome, String substringDescricao);

	List<Campanha> findByDonoEmail(String email);
	
	List<Campanha> findByOrderByLikesDesc();
}