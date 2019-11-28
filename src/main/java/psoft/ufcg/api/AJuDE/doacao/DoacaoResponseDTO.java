package psoft.ufcg.api.AJuDE.doacao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiModel(value = "Objeto para encapsular doações", description = "Objeto que encapsula o objeto Doacao enviado por um usuário para transacionar os devidos dados")
public class DoacaoResponseDTO {
	@ApiModelProperty(value = "Doação que está sendo efetuada", example = "0.01", position = 0)
	private Doacao doacao;
	
	private DoacaoResponseDTO(Doacao doacao) {
		super();
		this.doacao = doacao;
	}

	@ApiOperation(value = "Encapsula o objeto da doação para transferência")
	public static DoacaoResponseDTO objToDTO(Doacao doacao) {
        return new DoacaoResponseDTO(doacao);
    }
	
	public double getValor() {
		return this.doacao.getValor();
	}
	
	public String getDoador() {
		return this.doacao.getDoador().getEmail();
	}
	
	public String getCampanha() {
		return this.doacao.getCampanha().getIdentificadorURL();
	}
}
