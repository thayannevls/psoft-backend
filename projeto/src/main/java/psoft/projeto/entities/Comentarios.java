package psoft.projeto.entities;

public class Comentarios {
    private String comentario;
    private User user;

    public Comentarios(String comentario, User user) {
        this.comentario = comentario;
        this.user = user;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        return this.user.getFirstName() + " " + this.user.getLastName() + " - " + this.user.getEmail() + "\n" + this.comentario;
    }
}
