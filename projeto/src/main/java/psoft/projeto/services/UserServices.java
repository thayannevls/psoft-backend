package psoft.projeto.services;


import org.springframework.stereotype.Service;
import psoft.projeto.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class UserServices {
  private Map<String, User> users;

  public UserServices() {
    this.users = new HashMap<>();
  }

  public Map<String, User> getUsers() {
    return users;
  }

  public User insertUser(String firstName, String lastName, String email, long cartao, String senha) {
    User user = null;
    if (!this.users.containsKey(email)) {
      user = new User(firstName, lastName, email, cartao, senha);
      users.put(email, user);
      String msg = "Seja bem vindo(a) ao AJuDE!!";
      this.sendEmail(user.getEmail(), msg);
    }
    return user;
  }

  public User insertUser(User user) {
    if (!this.users.containsKey(user.getEmail())) {
      users.put(user.getEmail(), user);
      String msg = "Seja bem vindo(a) ao AJuDE!!";
      this.sendEmail(user.getEmail(), msg);
    } else{
      user = null;
    }
    return user;
  }



  private void sendEmail(String email, String message) {



  }


  public Optional<User> getUser(String email) {
    return Optional.ofNullable(this.users.get(email));
  }
}
