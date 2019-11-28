package psoft.ufcg.api.AJuDE.campanha.comentario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiModel(value = "Objeto de transferência de comentário", description = "Objeto que encapsula os dados de um comentário, utilizado para transferi-los pela rede")
public class ComentarioDTO {
	@ApiModelProperty(value = "Comentário em si", example = "Muito bom", position = 0)
	private String comentario;

	@ApiOperation(value = "Cria um objeto comentário a partir da string comentário")
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
