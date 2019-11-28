package psoft.ufcg.api.AJuDE.usuario;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
	
    private final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

	public Usuario save(Usuario usuario) throws ServletException {
		if (this.usuarioDAO.existsById(usuario.getEmail())) {
			throw new ResourceConflictException("E-mail já cadastrado");
		}
		this.enviarEmail(usuario);
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
	
	public List<Campanha> getParticipacoes(String email, String substring) {
		if(substring == null)
			return this.getParticipacoes(email);
		
		return this.getParticipacoesWithSubstring(email, substring);
	}

	private List<Campanha> getParticipacoes(String email) {
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
	
	private List<Campanha> getParticipacoesWithSubstring(String email, String substring) {
		Optional<Usuario> usuario = this.findByEmail(email);
		if (!usuario.isPresent()) {
			throw new ResourceNotFoundException("Usuário não cadastrado");
		}
		
		List<Campanha> participacoes = this.campanhaService.getByUsuarioEmail(email, substring);
		this.doacaoService.getByUsuarioEmail(email, substring).stream()
				.forEach(d -> participacoes.add(d.getCampanha()));
		
		Set<String> set = new HashSet<>(participacoes.size());
		participacoes.removeIf(p -> !set.add(p.getIdentificadorURL()));

		return participacoes;
	}
	
	private void enviarEmail(Usuario usuario){
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        javaMailSender.setUsername("ajudeapppsoft@gmail.com");
        javaMailSender.setPassword("ajudeapp123");
        javaMailSender.setJavaMailProperties(props);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Seja Bem Vindo ao AJuDE");
        message.setText("Olá, " + usuario.getPrimeiroNome() + System.lineSeparator()
                + System.lineSeparator() + "Seja bem vindo ao AJuDE. Comece agora a contribuir e a criar campanhas: " +
                 System.lineSeparator() + "https://thayannevls.github.io/psoft-frontend"+ System.lineSeparator() + System.lineSeparator() +
                "Equipe AJuDE.");
        message.setTo(usuario.getEmail());
        message.setFrom("AJuDE <ajudeapppsoft@gmail.com>");

        javaMailSender.send(message);
    }
}
