package psoft.ufcg.api.AJuDE.campanha.comentario;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository<T, ID extends Serializable> extends JpaRepository<Comentario, Integer> {

}
