package psoft.ufcg.api.AJuDE.campanha;

import java.util.List;
import java.util.Optional;

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
}
