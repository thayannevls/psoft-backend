package psoft.ufcg.api.AJuDE.campanha;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import psoft.ufcg.api.AJuDE.usuario.Usuario;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

@Entity
@Table(name = "tb_campanha")
public class Campanha {
  @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private String nomeCurto;
  private String identificadorURL;
  private String descricao;
  private String dataArrecadacao; //  dia/mes/ano
  private String status;
  private double meta;
  private double reaisDoados;
  private ArrayList<String> doacaes;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "email")
  private Usuario adm;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "idComentario")
  @Embedded
  private List<Comentario> comentarios;
  private int likes;


  public Campanha(int id, String nomeCurto, String identificadorURL, String descricao, String dataArrecadacao, double meta, Usuario adm) {
    this.id = id;
    this.nomeCurto = nomeCurto;
    this.identificadorURL = identificadorURL;
    this.descricao = descricao;
    this.dataArrecadacao = dataArrecadacao;
    this.meta = meta;
    this.adm = adm;
    this.likes = 0;
    this.reaisDoados = 0;
    this.comentarios = new ArrayList<>();
    this.doacaes = new ArrayList<>();
    this.setStatus();
  }

  public Campanha(String nomeCurto, String identificadorURL, String descricao, String dataArrecadacao, double meta) {
    this.nomeCurto = nomeCurto;
    this.identificadorURL = identificadorURL;
    this.descricao = descricao;
    this.dataArrecadacao = dataArrecadacao;
    this.meta = meta;
    this.likes = 0;
    this.reaisDoados = 0;
    this.comentarios = new ArrayList<>();
    this.doacaes = new ArrayList<>();
    this.setStatus();
    this.adm = new Usuario();
  }

  public Campanha() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNomeCurto() {
    return nomeCurto;
  }

  public void setNomeCurto(String nomeCurto) {
    this.nomeCurto = nomeCurto;
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

  public String getDataArrecadacao() {
    return dataArrecadacao;
  }

  public void setDataArrecadacao(String dataArrecadacao) {
    this.dataArrecadacao = dataArrecadacao;
  }

  public String getStatus() {
    this.setStatus();
    return status;
  }

  public void setStatus() {
    String today = Calendar.getInstance().toString();
    if (compareDates(this.dataArrecadacao, today) >= 0) {
      this.status = "ATIVA";
    } else if (compareDates(this.dataArrecadacao, today) < 0) {
      if (this.reaisDoados >= this.meta) {
        this.status = "CONCLUIDA";
      } else {
        this.status = "VENCIDA";
      }
    }
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

  public Usuario getAdm() {
    return adm;
  }

  public void setAdm(Usuario adm) {
    this.adm = adm;
  }

  public List<Comentario> getComentarios() {
    //EntityManager em = (EntityManager) new Campanha();
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
