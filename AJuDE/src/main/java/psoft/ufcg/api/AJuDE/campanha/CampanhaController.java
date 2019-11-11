package psoft.ufcg.api.AJuDE.campanha;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campanha")
public class CampanhaController {
	
	@Autowired
    CampanhaService campanhaService;
    
    @GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
    public String get() {
    	return "oi";
    }

    @PostMapping("/")
    public ResponseEntity<Campanha> cadastraCampanha(@RequestBody Campanha campanha) throws ServletException {
        return new ResponseEntity<Campanha>(this.campanhaService.cadastrarCampanha(campanha), HttpStatus.OK);
    }

}