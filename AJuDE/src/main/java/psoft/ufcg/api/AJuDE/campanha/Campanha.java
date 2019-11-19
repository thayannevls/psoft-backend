package psoft.ufcg.api.AJuDE.campanha;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import psoft.ufcg.api.AJuDE.usuario.Usuario;

@Entity
@Table(name = "tb_campanha")
public class Campanha {
  @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private String nome;
  private String identificadorURL;
  private String descricao;
  private String deadline; //  dia/mes/ano
  private String status;
  private double meta;
  private double reaisDoados;
  private int likes;
  private ArrayList<String> doacaes;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "email")
  @JsonIgnore
  private Usuario dono;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "idComentario")
  @Embedded
  @JsonIgnore
  private List<Comentario> comentarios;


  public Campanha(int id, String nome, String identificadorURL, String descricao, String deadline, double meta, Usuario dono) {
    this.id = id;
    this.nome = nome;
    this.identificadorURL = identificadorURL;
    this.descricao = descricao;
    this.deadline = deadline;
    this.meta = meta;
    this.dono = dono;
    this.likes = 0;
    this.reaisDoados = 0;
    this.comentarios = new ArrayList<>();
    this.doacaes = new ArrayList<>();
    this.setStatus();
  }

  public Campanha(String nome, String identificadorURL, String descricao, String deadline, double meta) {
    this.nome = nome;
    this.identificadorURL = identificadorURL;
    this.descricao = descricao;
    this.deadline = deadline;
    this.meta = meta;
    this.likes = 0;
    this.reaisDoados = 0;
    this.comentarios = new ArrayList<>();
    this.doacaes = new ArrayList<>();
    this.setStatus();
    this.dono = new Usuario();
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

  public void setDeadline(String deadline) {
    this.deadline = deadline;
  }

  public String getStatus() {
    this.setStatus();
    return status;
  }

  public void setStatus() {
//    String today = Calendar.getInstance().toString();
//    if (compareDates(this.deadline, today) >= 0) {
//      this.status = "ATIVA";
//    } else if (compareDates(this.deadline, today) < 0) {
//      if (this.reaisDoados >= this.meta) {
//        this.status = "CONCLUIDA";
//      } else {
//        this.status = "VENCIDA";
//      }
//    }
	  this.status = "ATIVA";
  }

  /**
   * Calcula qual data é mais recente.
   * Se resultado > 0, então date1 está no futuro em relação a date2
   * Se resultado < 0, então date1 está no passado em relação a date2
   * Se resultado == 0, então date1 é igual a date2
   * @param date1
   * @param date2
   * @return
   */
  private int compareDates(String date1, String date2) {
    int result = 0;
    String[] d1 = date1.split("/");
    String[] d2 = date2.split("/");

    for (int i = d1.length-1; i >= 0; i++){
      if (Integer.parseInt(d1[i]) != Integer.parseInt(d2[i])){
        result = Integer.parseInt(d1[i]) - Integer.parseInt(d2[i]);
      }
    }
    return result;
  }

  public void encerrarCampanha() {
    this.status = "ENCERRADA";
  }

  public double getMeta() {
    return meta;
  }

  public void setMeta(double meta) {
    this.meta = meta;
  }

  public ArrayList<String> getDoacaes() {
    return doacaes;
  }

  public void addDoacaes(String doacao, double valorDoacao) {
    this.reaisDoados += valorDoacao;
    this.doacaes.add(doacao);
  }

  public Usuario getDono() {
    return dono;
  }

  public void setDono(Usuario dono) {
    this.dono = dono;
  }

  public List<Comentario> getComentarios() {
    return this.comentarios;
  }

  public void addComentarios(String comentario, Usuario user) {
    Comentario newComentario = new Comentario(comentario, user);
    this.comentarios.add(newComentario);
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

}
