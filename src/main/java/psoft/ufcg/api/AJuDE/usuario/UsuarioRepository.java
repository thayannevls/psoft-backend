package psoft.ufcg.api.AJuDE.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository<T, ID> extends JpaRepository<Usuario, String> {

}
