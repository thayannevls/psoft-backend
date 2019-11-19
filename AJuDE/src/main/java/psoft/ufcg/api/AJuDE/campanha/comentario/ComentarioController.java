package psoft.ufcg.api.AJuDE.campanha.comentario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.campanha.CampanhaService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/campanhas")
public class ComentarioController {
	
	@Autowired
	ComentarioService comentarioService;
	
	@Autowired
	CampanhaService campanhaService;
	
	@PostMapping("/{campanhaIdURL}")
	public ResponseEntity<Comentario> create(@PathVariable String campanhaIdURL, @RequestBody Comentario comentario) {
		Campanha campanha = getCampanhaByIdURL(campanhaIdURL);
		comentario.setCampanha(campanha);
		return new ResponseEntity<Comentario>(this.comentarioService.save(comentario), HttpStatus.CREATED);
	}
	
	
	private Campanha getCampanhaByIdURL(String identificadorURL) {
		Campanha campanha = this.campanhaService.findByIdURL(identificadorURL);
		
		if(campanha.isEmpty()) 
			throw new ResourceNotFoundException("Campanha não existe.");
		
		return campanha;
	}
}
