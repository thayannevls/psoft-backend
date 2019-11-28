package psoft.ufcg.api.AJuDE.campanha.like;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Objeto de transferência de likes", description = "Objeto que encapsula os dados de um like, utilizado para transferi-los pela rede")
public class LikeResponseDTO {
	@ApiModelProperty(value = "Like efetuado", example = "3124", position = 0)
	private Like like;
	@ApiModelProperty(value = "Ação a ser executada sobre o Like", example = "add", position = 1)
	private String action;
	
	private LikeResponseDTO(Like like, String action) {
		super();
		this.like = like;
		this.action = action;
	}
	
	public static LikeResponseDTO objToDTO(Like like, String action) {
		return new LikeResponseDTO(like, action);
	}
	
	public static LikeResponseDTO objToDTO(Like like) {
		return new LikeResponseDTO(like, "add");
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

