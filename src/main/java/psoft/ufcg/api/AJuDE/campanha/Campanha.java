package psoft.ufcg.api.AJuDE.campanha;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.management.InvalidAttributeValueException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import psoft.ufcg.api.AJuDE.usuario.Usuario;

/**
 * Um objeto campanha modela uma campanha real no sistema AJuDE. Cada campanha possui um ID único, nome, descrição, deadline e uma meta.
 * Também contém um estado, que pode ser ATIVA, ENCERRADA ou VENCIDA.
 * 
 * @author Thayanne Luiza Victor Landim Sousa
 * @author Vítor Braga Diniz
 * 
 * @version 1.0
 */
@Entity
@Table(name = "tb_campanha")
public class Campanha {
	@Transient
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;  
	@Column(unique = true)
	private String identificadorURL;

	private String nome;
	private String descricao;
	private String deadline;
	private double meta;
	private double reaisDoados;
	private boolean encerradaPeloUsuario;
	private int likes;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email")
	@JsonIgnore
	private Usuario dono;

	public Campanha(int id, String nome, String identificadorURL, String descricao, String deadline, double meta, Usuario dono) throws InvalidAttributeValueException {
		this.id = id;
		this.nome = nome;
		this.identificadorURL = identificadorURL;
		this.descricao = descricao;
		this.setDeadline(deadline);
		this.meta = meta;
		this.dono = dono;
		this.likes = 0;
		this.reaisDoados = 0;
		this.encerradaPeloUsuario = false;
	}

	public Campanha(String nome, String identificadorURL, String descricao, String deadline, double meta) throws InvalidAttributeValueException {
		this.nome = nome;
		this.identificadorURL = identificadorURL;
		this.descricao = descricao;
		this.setDeadline(deadline);
		this.meta = meta;
		this.likes = 0;
		this.reaisDoados = 0;
		this.dono = new Usuario();
		this.encerradaPeloUsuario = false;
	}

	public Campanha() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) throws InvalidAttributeValueException {
		this.deadline = deadline;
		LocalDate deadlineDate = getDeadlineAsDate();
		if(deadlineDate.isBefore(LocalDate.now())) {
			throw new InvalidAttributeValueException("Deadline da campanha deve ser uma data válida.");
		}
	}
	
	@JsonIgnore
	public LocalDate getDeadlineAsDate() {
		return LocalDate.parse(this.deadline, this.dateFormatter);
	}

	public String getStatus() {
		if(this.encerradaPeloUsuario) {
			return "ENCERRADA";
		}
		
		LocalDate today = LocalDate.now();
		LocalDate deadline = getDeadlineAsDate();
		
		if(this.reaisDoados >= this.meta)
			return "CONCLUIDA";

		if(deadline.isBefore(today)) {
			
			return "VENCIDA";
		}
		
		return "ATIVA";
	}
	
	@JsonIgnore
	public void encerrarCampanha() {
		this.encerradaPeloUsuario = true;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}
	
	public Usuario getDono() {
		return dono;
	}

	public void setDono(Usuario dono) {
		this.dono = dono;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public double getReaisDoados() {
		return this.reaisDoados;
	}

	@JsonIgnore
	public void addLike() {
		this.likes ++;
	}
	
	@JsonIgnore
	public void removeLike() {
		this.likes --;
	}
	
	@JsonIgnore
	public void addDoacao(double doacao) {
		this.reaisDoados += doacao;
	}
	
	@JsonIgnore
	public double getRestante() {
		return this.meta - this.reaisDoados;
	}
	
	@JsonIgnore
	public boolean isEmpty() {
		return this.identificadorURL.isEmpty();
	}
}