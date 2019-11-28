package psoft.ufcg.api.AJuDE.campanha.comentario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@ApiModel(value = "Comentário", description = "Modelo de um comentário. Esse modelo representa a entidade comentário no banco de dados e possui as funções básicas de getters e setters para seus atributos.")
@Entity
@Table(name = "tb_comentario")
public class Comentario {

	@ApiModelProperty(value = "Identificador único do comentário.", example = "231354", position = 0)
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ApiModelProperty(value = "Usuário dono do comentário", example = "Severina de Araújo", position = 1)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "usuario_email", nullable = false)
	@JsonIgnore
	private Usuario usuario;

	@ApiModelProperty(value = "Campanha que recebeu o comentário", example = "Campanha do Milhão", position = 2)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campanha_id", nullable = false)
    @JsonIgnore
	private Campanha campanha;

	@ApiModelProperty(value = "Comentário pai (caso seja uma resposta)", position = 3)
	@ManyToOne
	@JsonBackReference(value = "parent")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Comentario parent;

	@ApiModelProperty(value = "Lista de respostas do comentário", position = 4)
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Comentario> respostas;
	
	private String comentario;

	public Comentario(String comentario, Usuario usuario) {
		this.comentario = comentario;
		this.usuario = usuario;
		this.respostas = new ArrayList<Comentario>();
	}
	
	public Comentario(String comentario) {
		this.comentario = comentario;
		this.respostas = new ArrayList<Comentario>();
	}
	
	public Comentario() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Comentario getParent() {
		return parent;
	}

	public void setParent(Comentario parent) {
		this.parent = parent;
	}

	public List<Comentario> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Comentario> respostas) {
		this.respostas = respostas;
	}
	
	@JsonIgnore
	public void addResposta(Comentario comentario) {
		this.respostas.add(comentario);
	}

	@Override
	public String toString() {
		return this.usuario.getPrimeiroNome() + " " + this.usuario.getUltimoNome() + " - " + this.usuario.getEmail() + "\n" 
				+ this.campanha.getIdentificadorURL() + "\n" + this.comentario;
	}
}
