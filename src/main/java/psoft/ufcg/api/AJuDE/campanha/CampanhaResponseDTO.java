package psoft.ufcg.api.AJuDE.campanha;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiModel(value = "Objeto de transferência de campanha", description = "Objeto que encapsula os dados de uma campanha, utilizada para transferi-los pela rede")
public class CampanhaResponseDTO {
	@ApiModelProperty(value = "Objeto da campanha.", example = "Campanha do Milhão", position = 0)
	private Campanha campanha;
	
	private CampanhaResponseDTO(Campanha campanha) {
		super();
		this.campanha = campanha;
	}

	@ApiOperation(value = "Encapsula diretamente uma campanha no objeto de transferência de dados (DTO)")
	public static CampanhaResponseDTO objToDTO(Campanha campanha) {
        return new CampanhaResponseDTO(campanha);
    }

	public int getId() {
		return this.campanha.getId();
	}
	
	public String getIdentificadorURL() {
		return this.campanha.getIdentificadorURL();
	}
	
	public String getNome() {
		return this.campanha.getNome();
	}
	
	public String getDescricao() {
		return this.campanha.getDescricao();
	}
	
	public String getStatus() {
		return this.campanha.getStatus();
	}
	
	public double getMeta() {
		return this.campanha.getMeta();
	}
	
	public String getDono() {
		return this.campanha.getDono().getEmail();
	}
	
	public double getDoacoes() {
		return this.campanha.getReaisDoados();
	}
	
	public int getLikes() {
		return this.campanha.getLikes();
	}
	
	public String getDeadline() {
		return this.campanha.getDeadline();
	}
	
	public double getReaisDoados() {
		return this.campanha.getReaisDoados();
	}
}
