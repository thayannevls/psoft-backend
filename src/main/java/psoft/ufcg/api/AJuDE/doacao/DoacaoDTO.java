package psoft.ufcg.api.AJuDE.doacao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiModel(value = "Objeto para realizar doações", description = "Objeto que encapsula o valor enviado por um usuário para realizar uma doação")
public class DoacaoDTO {
	@ApiModelProperty(value = "Valor que está sendo doado", example = "0.01", position = 0)
	private double valor;

	@ApiOperation(value = "Encapsula o valor da doação para transferência do dado")
	public Doacao get() {
		return new Doacao(valor);
	}

	@ApiOperation(value = "Recupera o valor da doação encapsulado para transferência")
	public double getValor() {
		return valor;
	}
}
