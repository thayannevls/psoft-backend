package psoft.ufcg.api.AJuDE.doacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import psoft.ufcg.api.AJuDE.campanha.Campanha;

@Service
public class DoacaoService {
	@Autowired
	private DoacaoRepository<Doacao, Long> doacaoDAO;

	public Doacao save(Doacao doacao) {
		return this.doacaoDAO.save(doacao);
	}

	public List<Doacao> getByCampanhaIdURL(String campanhaIdURL) {
		return this.doacaoDAO.findByCampanhaIdentificadorURL(campanhaIdURL);
	}

	public List<Doacao> getByUsuarioEmail(String email) {
		return this.doacaoDAO.findByDoadorEmail(email);
	}
	
	public List<Doacao> getByUsuarioEmail(String email, String substring) {
		return this.doacaoDAO
				.findByDoadorEmailAndCampanhaNomeContainsIgnoreCaseOrCampanhaDescricaoContainsIgnoreCase(email, substring, substring);
	}
}
