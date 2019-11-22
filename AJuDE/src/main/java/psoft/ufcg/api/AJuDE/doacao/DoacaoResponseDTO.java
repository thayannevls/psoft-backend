package psoft.ufcg.api.AJuDE.doacao;


public class DoacaoResponseDTO {
	private Doacao doacao;
	
	private DoacaoResponseDTO(Doacao doacao) {
		super();
		this.doacao = doacao;
	}
	
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
