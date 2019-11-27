package psoft.ufcg.api.AJuDE.usuario;

public class UsuarioResponseDTO {
	private Usuario usuario;
	
	private UsuarioResponseDTO(Usuario usuario) {
		super();
		this.usuario = usuario;
	}
	
	public static UsuarioResponseDTO objToDTO(Usuario usuario) {
		return new UsuarioResponseDTO(usuario);
	}
	
	public String getEmail() {
		return this.usuario.getEmail();
	}
	
	public String getPrimeiroNome() {
		return this.usuario.getPrimeiroNome();
	}
	
	public String getUltimoNome() {
		return this.usuario.getUltimoNome();
	}
}
