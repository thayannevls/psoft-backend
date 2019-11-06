package psoft.projeto.entities;

/**
 * User representa um usuário do sistema. Cada usuário tem primeiro nome,
 * ultimo nome, e-mail, número do cartão de crédito e senha
 */
public class User {

  private String firstName;
  private String lastName;
  private String email;
  private long cartao;
  private String token;

  /**
   * construtor da classe User que inicializa cada atributo com o valor passado a ele
   *
   * @param firstName
   * @param lastName
   * @param email
   * @param cartao
   * @param token
   */
  public User(String firstName, String lastName, String email, long cartao, String token) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.cartao = cartao;
    this.token = token;
  }

  public User() {
  }

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

  public String getToken() {
    return this.token;
  }

  public void setToken(String senha) {
    this.token = senha;
  }

  public boolean isEmpty(){
    return this.email == null;
  }
}
