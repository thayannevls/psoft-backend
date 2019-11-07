package psoft.projeto.services;

import psoft.projeto.entities.Campanha;
import psoft.projeto.entities.User;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CampanhaService {

    private Map<String, User> users;
    private Map<Integer, Campanha> campanhas;


    public CampanhaService(Map<String, User> users, Map<Integer, Campanha> campanhas) {
        this.users = users;
        this.campanhas = campanhas;
    }

    public CampanhaService(Map<String, User> users) {
        this.users = users;
        this.campanhas = new HashMap<>();
    }


    public void cadastrarCampanha(String nomeCurto, String identificadorURL, String descricao, Date dataArrecadacao, String status, double meta, User adm) throws ServletException {
        if (this.users.containsKey(adm.getEmail())){
            int id = this.generateId();
            Campanha campanha = new Campanha(id, nomeCurto, identificadorURL, descricao, dataArrecadacao, status, meta, adm);
            this.campanhas.put(id, campanha);
        } else{
            throw new ServletException("Usuário não cadastrado!");
        }
    }

    private int generateId() {
        int id = this.campanhas.size()+1;
        if (this.campanhas.containsKey(id)){
            id = 1;
            while (this.campanhas.containsKey(id)){
                id++;
            }
        }
        return id;
    }


}
