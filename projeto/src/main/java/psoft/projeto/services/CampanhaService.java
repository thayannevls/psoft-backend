package psoft.projeto.services;

import com.sun.xml.fastinfoset.util.CharArray;
import psoft.projeto.entities.Campanha;
import psoft.projeto.entities.User;

import javax.servlet.ServletException;
import java.lang.reflect.Array;
import java.util.*;

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


    public Campanha cadastrarCampanha(String nomeCurto, String identificadorURL, String descricao, Date dataArrecadacao, String status, double meta, User adm) throws ServletException {
        Campanha campanha = new Campanha();
        if (this.users.containsKey(adm.getEmail())){
            int id = this.generateId();
            nomeCurto = formataNomeCurto(nomeCurto);
            campanha = new Campanha(id, nomeCurto, identificadorURL, descricao, dataArrecadacao, status, meta, adm);
            this.campanhas.put(id, campanha);
        } else{
            throw new ServletException("Usuário não cadastrado!");
        }
        return campanha;
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
