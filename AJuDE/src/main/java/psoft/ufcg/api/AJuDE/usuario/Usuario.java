package psoft.ufcg.api.AJuDE.usuario;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {
	
	@Id
	private String email;
	private String primeiroNome;
	private String ultimoNome;
	private long cartao;
	private String senha;

	/**
	 * construtor da classe User que inicializa cada atributo com o valor passado a ele
	 *
	 * @param primeiroNome
	 * @param lastName
	 * @param email
	 * @param cartao
	 * @param senha
	 */
	public Usuario(String primeiroNome, String ultimoNome, String email, long cartao, String senha) {
		super();
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

	public boolean isEmpty(){
		return this.email == null;
	}
}
