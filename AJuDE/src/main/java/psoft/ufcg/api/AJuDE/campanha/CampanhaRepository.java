package psoft.ufcg.api.AJuDE.campanha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampanhaRepository<T, ID> extends JpaRepository<Campanha, Integer> {
}
