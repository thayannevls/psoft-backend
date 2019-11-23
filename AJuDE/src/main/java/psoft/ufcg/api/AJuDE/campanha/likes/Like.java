package psoft.ufcg.api.AJuDE.campanha.likes;

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
@Table(name = "tb_like")
public class Like {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campanha_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Campanha campanha;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "usuario_email", nullable = false)
	private Usuario usuario;
	
	public Like(Campanha campanha, Usuario usuario) {
		super();
		this.campanha = campanha;
		this.usuario = usuario;
	}

	public Like() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Like [id=" + id + ", campanha=" + campanha.getIdentificadorURL() + ", usuario=" + usuario.getEmail() + "]";
	}
}
