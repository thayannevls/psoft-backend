package psoft.ufcg.api.AJuDE.campanha;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import psoft.ufcg.api.AJuDE.auth.JwtService;
import psoft.ufcg.api.AJuDE.usuario.Usuario;
import psoft.ufcg.api.AJuDE.usuario.UsuarioRepository;

@Service
public class CampanhaService {

    private UsuarioRepository<Usuario, String> usuarios;
    private CampanhaRepository<Campanha, Integer> campanhas;
    private int id;

    public CampanhaService(UsuarioRepository<Usuario, String> usuarios, CampanhaRepository<Campanha, Integer> campanhas) {
        this.usuarios = usuarios;
        this.campanhas = campanhas;
        this.id = 1;
    }
    	
    public CampanhaService() {}
    
    public Campanha cadastrarCampanha(Campanha campanha, String userToken) throws ServletException {
        String userEmail = checkUser(userToken);
        if(this.usuarios.existsById(userEmail)){
            campanha.setNomeCurto(formataNomeCurto(campanha.getNomeCurto()));
            campanha.setAdm(this.usuarios.getOne(userEmail));
            campanha.setId(this.id++);
            this.campanhas.save(campanha);
        } else{
            throw new ServletException("Usuário não cadastrado!");
        }

        return campanha;
    }

    private String checkUser(String userToken) throws ServletException {
        JwtService jwt = new JwtService();
        return jwt.getTokenSubject(userToken);
    }

    private String formataNomeCurto(String nome) {
        nome = trocaPontuacaoPorEspaco(nome);
        nome = substituiCaracteresEspeciais(nome);
        nome.toLowerCase();
        nome = retiraEspacosMultiplos(nome);
        nome.replaceAll(" ", "-");

        return nome;
    }

    private String retiraEspacosMultiplos(String nome) {
        String result = "";
        int countSpaces = 0;
        for(int i = 0; i < nome.length();i++){
            if (nome.charAt(i) != ' '){
                if (countSpaces != 0){
                    result += " ";
                    countSpaces = 0;
                }
                result += nome.charAt(i);
            } else{
                countSpaces++;
            }
        }
        return result;
    }
    private String trocaPontuacaoPorEspaco(String nome) {
        nome.replaceAll("."," ");
        nome.replaceAll(","," ");
        nome.replaceAll(";"," ");
        nome.replaceAll(":"," ");
        nome.replaceAll("!"," ");
        nome.replaceAll("/"," ");
        nome.replaceAll("\\?"," ");

        return nome;
    }
    private String substituiCaracteresEspeciais(String nome) {
        String result = nome;
        ArrayList<String> especiais = new ArrayList<>();
        especiais.add("á");        especiais.add("à");        especiais.add("â");        especiais.add("ã");
        especiais.add("ä");        especiais.add("é");        especiais.add("è");        especiais.add("ê");
        especiais.add("í");        especiais.add("ì");        especiais.add("ï");        especiais.add("ó");
        especiais.add("ò");        especiais.add("ô");        especiais.add("õ");        especiais.add("ö");
        especiais.add("ú");        especiais.add("ù");        especiais.add("ü");        especiais.add("ç");
        for (int i = 0; i < especiais.size();i++) {
            if (nome.contains(especiais.get(i))){
                if (i <= 4){
                    result.replaceAll(especiais.get(i), "a");
                } else if ( i <= 7){
                    result.replaceAll(especiais.get(i), "e");
                } else if ( i <= 10){
                    result.replaceAll(especiais.get(i), "i");
                } else if ( i <= 15){
                    result.replaceAll(especiais.get(i), "o");
                } else if ( i <= 18){
                    result.replaceAll(especiais.get(i), "u");
                } else{
                    result.replaceAll(especiais.get(i), "c");
                }
            }
        }

        return result;
    }
}
