package psoft.ufcg.api.AJuDE.campanha.like;

public class LikeResponseDTO {
	private Like like;
	private String action;
	
	private LikeResponseDTO(Like like, String action) {
		super();
		this.like = like;
		this.action = action;
	}
	
	public static LikeResponseDTO objToDTO(Like like, String action) {
		return new LikeResponseDTO(like, action);
	}
	
	public String getCampanha() {
		return this.like.getCampanha().getIdentificadorURL();
	}
	
	public String getUsuario() {
		return this.like.getUsuario().getEmail();
	}
	
	public String getAction() {
		return this.action;
	}
}

