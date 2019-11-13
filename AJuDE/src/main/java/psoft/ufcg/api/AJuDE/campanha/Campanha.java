package psoft.ufcg.api.AJuDE.campanha;

import java.util.ArrayList;
import java.util.Calendar;
import psoft.ufcg.api.AJuDE.usuario.Usuario;
import javax.persistence.*;

@Entity
@Table(name = "campanha")
public class Campanha {
    @Id
    private int id;
    private String nomeCurto;
    private String identificadorURL;
    private String descricao;
    private Calendar dataArrecadacao;
    private String status;
    private double meta;
    private double reaisDoados;
    private ArrayList<String> doacaes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Usuario adm;

    private ArrayList<Comentario> comentarios;
    private int likes;


    public Campanha(int id, String nomeCurto, String identificadorURL, String descricao, Calendar dataArrecadacao, double meta, Usuario adm) {
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

    public Campanha(String nomeCurto, String identificadorURL, String descricao, Calendar dataArrecadacao, double meta) {
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

    public Calendar getDataArrecadacao() {
        return dataArrecadacao;
    }

    public void setDataArrecadacao(Calendar dataArrecadacao) {
        this.dataArrecadacao = dataArrecadacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus() {
        Calendar today = Calendar.getInstance();
        if (today.compareTo(this.dataArrecadacao) < 0){
            this.status = "ATIVA";
        } else if (today.compareTo(this.dataArrecadacao) >= 0){
            if (this.reaisDoados >= this.meta){
                this.status = "CONCLUIDA";
            } else {
                this.status = "VENCIDA";
            }
        }
    }

    public void encerrarCampanha(){
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

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
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
