package psoft.ufcg.api.AJuDE.usuario;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Usuário", description = "Modelo de um usuário. Esse modelo representa a entidade usuário no banco de dados e possui as funções básicas de getters e setters para seus atributos.")
@Entity
@Table(name = "tb_usuario")
public class Usuario {

  @ApiModelProperty(value = "E-mail: identificador único do usuário", example = "ze_da_silva@ccc.ufcg.edu.br", position = 0)
  @Id
  private String email;
  @ApiModelProperty(value = "Primeiro nome do usuário", example = "José", position = 1)
  private String primeiroNome;
  @ApiModelProperty(value = "Último nome do usuário", example = "Silva", position = 2)
  private String ultimoNome;
  @ApiModelProperty(value = "Número do cartão", example = "2423492598245982", position = 3)
  private long cartao;
  @ApiModelProperty(value = "Senha do usuário", example = "senha123", position = 4)
  private String senha;

  /**
   * construtor da classe User que inicializa cada atributo com o valor passado a ele
   *
   * @param primeiroNome
   * @param ultimoNome
   * @param email
   * @param cartao
   * @param senha
   */
  public Usuario(String primeiroNome, String ultimoNome, String email, long cartao, String senha) {
    this.primeiroNome = primeiroNome;
    this.ultimoNome = ultimoNome;
    this.email = email;
    this.cartao = cartao;
    this.senha = senha;
  }

  public Usuario() {
    super();
  }

  public String getPrimeiroNome() {
    return primeiroNome;
  }

  public void setPrimeiroNome(String primeiroNome) {
    this.primeiroNome = primeiroNome;
  }

  public String getUltimoNome() {
    return ultimoNome;
  }

  public void setUltimoNome(String ultimoNome) {
    this.ultimoNome = ultimoNome;
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
    return this.senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }
  
  @JsonIgnore
  public boolean isEmpty() {
    return this.email == null;
  }
}
