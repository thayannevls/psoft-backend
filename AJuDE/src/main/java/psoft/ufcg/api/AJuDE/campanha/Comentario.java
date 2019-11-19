package psoft.ufcg.api.AJuDE.campanha;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import psoft.ufcg.api.AJuDE.usuario.Usuario;

@Entity
@Table(name = "tb_comentario")
@Embeddable
public class Comentario {
  private String comentario;

  @OneToOne(fetch = FetchType.EAGER)
  private Usuario usuario;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

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

  public Usuario getUser() {
    return usuario;
  }

  public void setUser(Usuario user) {
    this.usuario = user;
  }

  @Override
  public String toString() {
    return this.usuario.getPrimeiroNome() + " " + this.usuario.getUltimoNome() + " - " + this.usuario.getEmail() + "\n" + this.comentario;
  }
}
