package psoft.ufcg.api.AJuDE.campanha;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;

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

	public Campanha editCampanha(Campanha campanha, Campanha mudancas) throws InvalidAttributeValueException {
		if (mudancas.getDeadline() != null && !mudancas.getDeadline().equals("")){
			campanha.setDeadline(mudancas.getDeadline());
		}
		if (mudancas.getDescricao() != null && !mudancas.getDescricao().equals("")){
			campanha.setDescricao(mudancas.getDescricao());
		}
		if (mudancas.getMeta() != 0){
			campanha.setMeta(mudancas.getMeta());
		}
		this.campanhaDAO.save(campanha);
		return campanha;
	}

	public Campanha save(Campanha campanha) {
		return this.campanhaDAO.save(campanha);
	}
	
	/**
	 * Filtra campanhas a partir de uma substring e lista de status.
	 * 
	 * Detalhe de implementação: O status teve que ser filtrado por fora, pois é um atributo dinâmico da classe e queries dinâmicas não conseguem capturar ele.
	 * @param substring
	 * @param status
	 * @return List<Campanha> Lista de campanhas
	 */
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

		if(sortMethod.equals("meta")) {
			ativas.sort((c1, c2) -> Double.compare(c1.getRestante(), c2.getRestante()));
		} else if(sortMethod.equals("deadline")) {
			ativas.sort((c1, c2) -> c1.getDeadlineAsDate().compareTo(c2.getDeadlineAsDate()));
		} else if(sortMethod.equals("likes")) {
			ativas.sort((c1, c2) -> Integer.compare(c1.getLikes(), c2.getLikes()));
		}
		
		return ativas;
	}
}
