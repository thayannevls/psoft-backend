package psoft.projeto.entities;

public class Usuario {

  private String firstName;
  private String lastName;
  private String email;
  private long cartao;
  private String senha;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public long getCartao() {
    return cartao;
  }

  public void setCartao(long cartao) {
    this.cartao = cartao;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }
}
