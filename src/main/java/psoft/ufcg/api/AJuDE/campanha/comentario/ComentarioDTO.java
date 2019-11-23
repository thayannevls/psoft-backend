package psoft.ufcg.api.AJuDE.campanha.comentario;

public class ComentarioDTO {
	private String comentario;
	
	public Comentario get() {
		return new Comentario(comentario);
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}	
}
