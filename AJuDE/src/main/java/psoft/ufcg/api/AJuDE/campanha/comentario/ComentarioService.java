package psoft.ufcg.api.AJuDE.campanha.comentario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {
	
	@Autowired
	private ComentarioRepository<Comentario, Integer> comentarioDAO;

	public Comentario save(Comentario comentario) {
		return this.comentarioDAO.save(comentario);
	}

	public List<Comentario> getAllByCampanhaId(String campanhaIdURL) {
		return this.comentarioDAO.findByCampanhaIdentificadorURL(campanhaIdURL);
	}

	public Optional<Comentario> findById(int comentarioId) {
		return this.comentarioDAO.findById(comentarioId);
	}
	
	public Optional<Comentario> deleteById(int comentarioId) {
		Optional<Comentario> comentario = this.findById(comentarioId);
		this.comentarioDAO.deleteById(comentarioId);
		return comentario;
	}

	public Comentario save(int comentarioId, Comentario comentario) {
		comentario.setParent(this.comentarioDAO.findById(comentarioId).get());
		return this.comentarioDAO.save(comentario);
	}
}
