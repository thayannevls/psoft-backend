package psoft.ufcg.api.AJuDE.doacao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import psoft.ufcg.api.AJuDE.auth.JwtService;
import psoft.ufcg.api.AJuDE.campanha.Campanha;
import psoft.ufcg.api.AJuDE.campanha.CampanhaService;
import psoft.ufcg.api.AJuDE.exceptions.ResourceNotFoundException;
import psoft.ufcg.api.AJuDE.exceptions.UnauthorizedException;
import psoft.ufcg.api.AJuDE.usuario.Usuario;

@RestController
@RequestMapping("/campanhas/{campanhaId}/doacoes")
public class DoacaoController {
	
	@Autowired
	private DoacaoService doacaoService;
	
	@Autowired
	private CampanhaService campanhaService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/")
	public ResponseEntity<DoacaoResponseDTO> create(@PathVariable String campanhaIdURL, @RequestBody DoacaoDTO doacaoDTO, 
			@RequestHeader("Authorization") String header) {
		Doacao doacao = doacaoDTO.get();
		Optional<Usuario> usuario = jwtService.getUsuarioByToken(header);
		if(!usuario.isPresent())
			throw new UnauthorizedException("Você precisa estar autenticado para fazer uma doação.");
		
		Campanha campanha = getCampanhaByIdURL(campanhaIdURL);
		
		doacao.setCampanha(campanha);
		doacao.setDoador(usuario.get());
		return new ResponseEntity<DoacaoResponseDTO>(DoacaoResponseDTO.objToDTO(this.doacaoService.save(doacao)), HttpStatus.CREATED);
	}
	
	/**
	 * Retorna um objeto campanha a partir de um identificador de URL. Caso não existe nenhuma campanha com esse identificador, 
	 * retorna um erro HTTP 404. 
	 * @param identificadorURL
	 * @return Campanha campanha com o Identificador de URL passado
	 */
	private Campanha getCampanhaByIdURL(String identificadorURL) {
		Campanha campanha = this.campanhaService.findByIdURL(identificadorURL);

		if(campanha.isEmpty()) 
			throw new ResourceNotFoundException("Campanha não existe.");

		return campanha;
	}
}
