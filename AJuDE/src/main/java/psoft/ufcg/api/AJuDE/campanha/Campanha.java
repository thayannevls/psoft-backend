package psoft.ufcg.api.AJuDE.campanha;

import java.util.ArrayList;
import java.util.Date;

import psoft.ufcg.api.AJuDE.usuario.Usuario;


public class Campanha {
    private int id;
    private String nomeCurto;
    private String identificadorURL;
    private String descricao;
    private Date dataArrecadacao;
    private String status;
    private double meta;
    private ArrayList<String> doacaes;
    private Usuario adm;
    private ArrayList<Comentario> comentarios;
    private int likes;


    public Campanha(int id, String nomeCurto, String identificadorURL, String descricao, Date dataArrecadacao, String status, double meta, Usuario adm) {
        this.id = id;
        this.nomeCurto = nomeCurto;
        this.identificadorURL = identificadorURL;
        this.descricao = descricao;
        this.dataArrecadacao = dataArrecadacao;
        this.status = status;
        this.meta = meta;
        this.doacaes = new ArrayList<>();
        this.adm = adm;
        this.comentarios = new ArrayList<>();
        this.likes = 0;
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

    public Date getDataArrecadacao() {
        return dataArrecadacao;
    }

    public void setDataArrecadacao(Date dataArrecadacao) {
        this.dataArrecadacao = dataArrecadacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void addDoacaes(String doacao) {
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
