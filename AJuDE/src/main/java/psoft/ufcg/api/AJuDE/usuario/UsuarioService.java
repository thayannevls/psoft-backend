package psoft.ufcg.api.AJuDE.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository<Usuario, String> usuarioDAO;

	public UsuarioService(UsuarioRepository<Usuario, String> usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public Usuario save(Usuario usuario) throws ServletException {

		if (this.usuarioDAO.existsById(usuario.getEmail())) {
			throw new ServletException("E-mail já cadastrado");
		}
		return usuarioDAO.save(usuario);

	}
	
	public Optional<Usuario> getUsuario(String email) {
		return usuarioDAO.findById(email);
	}
	
	public Optional<Usuario> remove(String email) throws ServletException {
		Optional<Usuario> usuario = this.getUsuario(email);
		if(usuario.isPresent()){
			usuarioDAO.deleteById(email);
		} else{
			throw new ServletException("Usuário não cadastrado");
		}
		return usuario;
	}
}
