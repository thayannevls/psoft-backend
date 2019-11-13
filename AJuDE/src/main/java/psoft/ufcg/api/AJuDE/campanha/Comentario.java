package psoft.ufcg.api.AJuDE.campanha;

import psoft.ufcg.api.AJuDE.usuario.Usuario;

import javax.persistence.*;

@Entity
@Table(name = "comentarios")
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
