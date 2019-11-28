package psoft.ufcg.api.AJuDE.doacao;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@ApiModel(value = "Doação", description = "Modelo de uma doação. Esse modelo representa a entidade doação no banco de dados e possui as funções básicas de getters e setters para seus atributos.")
@Entity
@Table(name = "tb_doacao")
public class Doacao {

	@ApiModelProperty(value = "Identificador único da doação", example = "653423", position = 0)
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "Usuário que realizou a doação", example = "José Vicente de Faria Lima", position = 1)
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usuario_email", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Usuario doador;

	@ApiModelProperty(value = "Campanha que recebeu a doação", example = "Campanha do Milhão", position = 2)
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "campanha_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Campanha campanha;

	@ApiModelProperty(value = "Valor doado em reais", example = "0.01", position = 3)
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
