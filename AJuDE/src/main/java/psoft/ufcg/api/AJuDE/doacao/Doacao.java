package psoft.ufcg.api.AJuDE.doacao;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@Entity
@Table(name = "tb_doacao")
public class Doacao {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_email", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Usuario doador;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campanha_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Campanha campanha;
	private double valor;

	public Doacao(Usuario doador, Campanha campanha, double valor) {
		super();
		this.doador = doador;
		this.campanha = campanha;
		this.valor = valor;
	}
	
	public Doacao(double valor) {
		super();
		this.valor = valor;
		this.campanha = null;
		this.doador = null;
	}
	
	public Doacao() {}

	public Usuario getDoador() {
		return doador;
	}

	public void setDoador(Usuario doador) {
		this.doador = doador;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
}
