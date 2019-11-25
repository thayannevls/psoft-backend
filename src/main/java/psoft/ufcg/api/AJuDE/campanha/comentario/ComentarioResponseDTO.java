package psoft.ufcg.api.AJuDE.campanha.comentario;

import java.util.List;
import java.util.stream.Collectors;

public class ComentarioResponseDTO {
	private Comentario comentario;
	
	private ComentarioResponseDTO(Comentario comentario) {
		super();
		this.comentario = comentario;
	}
	
	public static ComentarioResponseDTO objToDTO(Comentario comentario) {
        return new ComentarioResponseDTO(comentario);
    }
	
	public long getId() {
		return this.comentario.getId();
	}
	
	public String getComentario() {
		return this.comentario.getComentario();
	}
	
	public String getUsuario() {
		return this.comentario.getUsuario().getEmail();
	}
	
	public List<ComentarioResponseDTO> getRespostas() {
		return (this.comentario.getRespostas()
								.stream()
								.map(c -> objToDTO(c))
								.collect(Collectors.toList()));
	}
}
