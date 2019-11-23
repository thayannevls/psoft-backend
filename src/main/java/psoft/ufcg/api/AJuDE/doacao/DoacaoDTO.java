package psoft.ufcg.api.AJuDE.doacao;

public class DoacaoDTO {
	private double valor;

	public Doacao get() {
		return new Doacao(valor);
	}

	public double getValor() {
		return valor;
	}
}
