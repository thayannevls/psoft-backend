package psoft.ufcg.api.AJuDE.usuario;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.campanha.CampanhaService;
import psoft.ufcg.api.AJuDE.doacao.DoacaoService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceConflictException;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository<Usuario, String> usuarioDAO;
	
	@Autowired
	private DoacaoService doacaoService;
	
	@Autowired
	private CampanhaService campanhaService;

	public UsuarioService(UsuarioRepository<Usuario, String> usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public Usuario save(Usuario usuario) throws ServletException {
		if (this.usuarioDAO.existsById(usuario.getEmail())) {
			throw new ResourceConflictException("E-mail já cadastrado");
		}
		return usuarioDAO.save(usuario);
	}

	public Optional<Usuario> findByEmail(String email) {
		return usuarioDAO.findById(email);
	}

	public Optional<Usuario> remove(String email) throws ServletException {
		Optional<Usuario> usuario = this.findByEmail(email);
		if (usuario.isPresent()) {
			usuarioDAO.deleteById(email);
		} else {
			throw new ResourceNotFoundException("Usuário não cadastrado");
		}
		return usuario;
	}

	public List<Campanha> getParticipacoes(String email) {
		Optional<Usuario> usuario = this.findByEmail(email);
		if (!usuario.isPresent()) {
			throw new ResourceNotFoundException("Usuário não cadastrado");
		}
		
		List<Campanha> participacoes = this.campanhaService.getByUsuarioEmail(email);
		this.doacaoService.getByUsuarioEmail(email).stream()
				.forEach(d -> participacoes.add(d.getCampanha()));
		
		Set<String> set = new HashSet<>(participacoes.size());
		participacoes.removeIf(p -> !set.add(p.getIdentificadorURL()));

		return participacoes;
	}
}
