package psoft.ufcg.api.AJuDE.usuario;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiModel(value = "Objeto de transferência de usuário", description = "Objeto que encapsula os dados de um usuário, utilizado para transferi-los pela rede")
public class UsuarioResponseDTO {

	@ApiModelProperty(value = "Usuário que está sendo encapsulado para ser transferido", example = "Severino Gates", position = 0)
	private Usuario usuario;
	
	private UsuarioResponseDTO(Usuario usuario) {
		super();
		this.usuario = usuario;
	}

	@ApiOperation(value = "Encapsula diretamente um usuário no objeto de transferência de dados (DTO)")
	public static UsuarioResponseDTO objToDTO(Usuario usuario) {
		return new UsuarioResponseDTO(usuario);
	}

	@ApiOperation(value = "Recupera o e-mail do usuário encapsulado")

	public String getEmail() {
		return this.usuario.getEmail();
	}
	@ApiOperation(value = "Recupera o primeiro nome do usuário encapsulado")
	public String getPrimeiroNome() {
		return this.usuario.getPrimeiroNome();
	}
	@ApiOperation(value = "Recupera o último nome do usuário encapsulado")
	public String getUltimoNome() {
		return this.usuario.getUltimoNome();
	}
}
