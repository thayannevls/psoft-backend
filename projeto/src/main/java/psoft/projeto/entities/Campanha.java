package psoft.projeto.entities;

import java.util.ArrayList;
import java.util.Date;

public class Campanha {
    private int id;
    private String nomeCurto;
    private String identificadorURL;
    private String descricao;
    private Date dataArrecadacao;
    private String status;
    private double meta;
    private ArrayList<String> doacaes;
    private User adm;
    private ArrayList<Comentarios> comentarios;
    private int likes;


    public Campanha(int id, String nomeCurto, String identificadorURL, String descricao,
                    Date dataArrecadacao, String status, double meta, ArrayList<String> doacaes,
                    User adm, ArrayList<Comentarios> comentarios, int likes) {
        this.id = id;
        this.nomeCurto = nomeCurto;
        this.identificadorURL = identificadorURL;
        this.descricao = descricao;
        this.dataArrecadacao = dataArrecadacao;
        this.status = status;
        this.meta = meta;
        this.doacaes = doacaes;
        this.adm = adm;
        this.comentarios = comentarios;
        this.likes = likes;
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

    public User getAdm() {
        return adm;
    }

    public void setAdm(User adm) {
        this.adm = adm;
    }

    public ArrayList<Comentarios> getComentarios() {
        return comentarios;
    }

    public void addComentarios(String comentario, User user) {
        Comentarios newComentario = new Comentarios(comentario, user);
        this.comentarios.add(newComentario);
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

}
