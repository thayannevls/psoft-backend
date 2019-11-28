package psoft.ufcg.api.AJuDE.campanha.comentario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value = "Objeto de transferência de comentário", description = "Objeto que encapsula o objeto comentário, utilizado para transferi-los pela rede")
public class ComentarioResponseDTO {
	@ApiModelProperty(value = "Objeto comentário", example = "Muito bom", position = 0)
	private Comentario comentario;

	private ComentarioResponseDTO(Comentario comentario) {
		super();
		this.comentario = comentario;
	}

	@ApiOperation(value = "Encapsula um objeto comentário em um DTO")
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
