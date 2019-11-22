package psoft.ufcg.api.AJuDE.campanha.comentario;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@Entity
@Table(name = "tb_comentario")
public class Comentario {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "usuario_email", nullable = false)
	@JsonIgnore
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campanha_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private Campanha campanha;
	
	@ManyToOne
	@JsonBackReference(value = "parent")
	private Comentario parent;
	
	@OneToMany
	private List<Comentario> respostas;
	
	@Lob
	private String comentario;

	public Comentario(String comentario, Usuario usuario) {
		this.comentario = comentario;
		this.usuario = usuario;
	}
	
	public Comentario(String comentario) {
		this.comentario = comentario;
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
