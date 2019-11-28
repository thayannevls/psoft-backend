package psoft.ufcg.api.AJuDE.doacao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		List<Doacao> search = this.doacaoDAO.findByDoadorEmailAndCampanhaNomeContainsIgnoreCase(email, substring);
		search.addAll(this.doacaoDAO.findByDoadorEmailAndCampanhaDescricaoContainsIgnoreCase(email, substring));
		Set<String> set = new HashSet<>(search.size());
		search.removeIf(d -> !set.add(d.getCampanha().getIdentificadorURL()));
		
		return search;
	}
}
