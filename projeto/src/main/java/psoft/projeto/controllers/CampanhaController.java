package psoft.projeto.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psoft.projeto.entities.Campanha;
import psoft.projeto.entities.User;
import psoft.projeto.services.CampanhaService;

import javax.servlet.ServletException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class CampanhaController {

    CampanhaService campanhaService;

    public CampanhaController(CampanhaService campanhaService) {
        this.campanhaService = campanhaService;
    }

    @PostMapping("/campanha")
    public ResponseEntity<Campanha> cadastraCampanha(@RequestBody Campanha campanha) throws ServletException {
        return new ResponseEntity(this.campanhaService.cadastrarCampanha(campanha), HttpStatus.OK);
    }

}
