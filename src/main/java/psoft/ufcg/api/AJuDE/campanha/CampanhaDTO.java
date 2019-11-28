package psoft.ufcg.api.AJuDE.campanha;

import javax.management.InvalidAttributeValueException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import psoft.ufcg.api.AJuDE.exceptions.BadRequestException;

@ApiModel(value = "Objeto de transferência de campanha", description = "Objeto que encapsula os dados de uma campanha, utilizada para transferi-los pela rede")
public class CampanhaDTO {
	@ApiModelProperty(value = "Nome da campanha.", example = "Campanha do Milhão", position = 0)
	private String nome;
	@ApiModelProperty(value = "Identificador URL único da campanha.", example = "campanha-do-milhao", position = 1)
	private String identificadorURL;
	@ApiModelProperty(value = "Data limite para concluir a meta", example = "30/11/2019", position = 2)
	private String deadline;
	@ApiModelProperty(value = "Descrição da campanha.", example = "Quero doar 1 milhão para o hospital Laureano em João Pessoa, ajude-me a chegar nesse valor",  position = 3)
	private String descricao;
	@ApiModelProperty(value = "Quantidade de dinheiro almejada", example = "1000000", position = 4)
	private double meta;
	@ApiModelProperty(value = "Status da campanha", example = "ATIVA", position = 5)
	private String status;

	@ApiOperation(value = "Ativa ou encerra a campanha dependendo do status no DTO e a retorna")
	public Campanha get() {
		try {
			Campanha campanha = new Campanha(nome, identificadorURL, descricao, deadline, meta);
			if(this.status != null && this.status.equals("ENCERRADA")) {
				campanha.encerrarCampanha();
			}
			else if(this.status != null && this.status.equals("ATIVA")) {
				campanha.ativarCampanha();
			}
			return campanha;
		} catch (InvalidAttributeValueException e) {
			throw new BadRequestException("Deadline inválida.");
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdentificadorURL() {
		return identificadorURL;
	}

	public void setIdentificadorURL(String identificadorURL) {
		this.identificadorURL = identificadorURL;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
