package psoft.ufcg.api.AJuDE.campanha;

public class CampanhaResponseDTO {
	private Campanha campanha;
	
	private CampanhaResponseDTO(Campanha campanha) {
		super();
		this.campanha = campanha;
	}
	
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
}
