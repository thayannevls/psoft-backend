package psoft.ufcg.api.AJuDE.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository<Usuario, String> usuarioDAO;

	public Usuario save(Usuario usuario) {
		return usuarioDAO.save(usuario);
	}
	
	public Optional<Usuario> getUsuario(String email) {
		return usuarioDAO.findById(email);
	}
	
	public Optional<Usuario> remove(String email) {
		Optional<Usuario> usuario = this.getUsuario(email);
		if(usuario.isPresent())
			usuarioDAO.deleteById(email);
		
		return usuario;
	}
}
