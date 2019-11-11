package psoft.ufcg.api.AJuDE.campanha;

import psoft.ufcg.api.AJuDE.usuario.Usuario;

public class Comentario {
    private String comentario;
    private Usuario usuario;

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
    public String toString(){
        return this.usuario.getPrimeiroNome() + " " + this.usuario.getUltimoNome() + " - " + this.usuario.getEmail() + "\n" + this.comentario;
    }
}
