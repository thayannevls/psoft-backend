package psoft.ufcg.api.AJuDE.campanha.comentario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {
	
	@Autowired
	private ComentarioRepository<Comentario, Integer> comentarioDAO;

	public Comentario save(Comentario comentario) {
		return this.comentarioDAO.save(comentario);
	}
}
