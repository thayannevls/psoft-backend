package psoft.ufcg.api.AJuDE.campanha;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampanhaService {

	@Autowired
	private CampanhaRepository<Campanha, Integer> campanhaDAO;
	
	public List<Campanha> findAll() {
		return this.campanhaDAO.findAll();
	}

	public Optional<Campanha> findById(int id) {
		return this.campanhaDAO.findById(id);
	}
	
	public Campanha findByIdURL(String identificadorURL) {
		return this.campanhaDAO.findByIdentificadorURL(identificadorURL);
	}

	public Campanha save(Campanha campanha) {
		return this.campanhaDAO.save(campanha);
	}
	
	public List<Campanha> findBySubstring(String substring, List<String> status) {
		List<Campanha> matches = this.campanhaDAO.findBySubstring(substring).stream()
									.filter(c -> status.contains(c.getStatus()))
									.collect(Collectors.toList());
		return matches;
	}

	public List<Campanha> getByUsuarioEmail(String email) {
		return this.campanhaDAO.findByDonoEmail(email);
	}
	
	public List<Campanha> getRank(String sortMethod) {
		List<Campanha> ativas = this.campanhaDAO.findAll().stream()
				.filter(c -> c.getStatus().equals("ATIVA"))
				.collect(Collectors.toList());

		if(sortMethod == "meta") {
			ativas.sort((c1, c2) -> Double.compare(c1.getRestante(), c2.getRestante()));
		} else if(sortMethod == "deadline") {
			ativas.sort((c1, c2) -> c1.getDeadlineAsDate().compareTo(c2.getDeadlineAsDate()));
		} else if(sortMethod == "likes") {
			ativas.sort((c1, c2) -> Integer.compare(c1.getLikes(), c2.getLikes()));
		}
		
		return ativas;
	}
}
