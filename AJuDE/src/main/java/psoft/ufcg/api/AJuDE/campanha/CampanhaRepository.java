package psoft.ufcg.api.AJuDE.campanha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CampanhaRepository<T, ID extends Serializable> extends JpaRepository<Campanha, Integer> {

	Campanha findByIdentificadorURL(String identificadorURL);
}
