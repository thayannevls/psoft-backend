package psoft.ufcg.api.AJuDE.campanha.comentario;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@Entity
@Table(name = "tb_comentario")
@Embeddable
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "email")
	@JsonIgnore
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCampanha")
	@JsonIgnore
	private Campanha campanha;

	private String comentario;

	public Comentario(String comentario, Usuario usuario) {
		this.comentario = comentario;
		this.usuario = usuario;
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

	@Override
	public String toString() {
		return this.usuario.getPrimeiroNome() + " " + this.usuario.getUltimoNome() + " - " + this.usuario.getEmail() + "\n" 
				+ this.campanha.getIdentificadorURL() + "\n" + this.comentario;
	}
}
