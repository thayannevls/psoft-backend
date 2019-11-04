package psoft.projeto.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserServices {
  private Map<String, User> users;

  public UserServices() {
    this.users = new HashMap<>();
  }

  public Map<String, User> getUsers() {
    return users;
  }

  public void insertUser(String firstName, String lastName, String email, long cartao, String senha) {
    if (!this.users.containsKey(email)) {
      User user = new User(firstName, lastName, email, cartao, senha);
      users.put(email, user);
      String msg = "";
      this.sendEmail(user, msg);
    }
  }

  public void insertUser(User user) {
    if (!this.users.containsKey(user.getEmail())) {
      users.put(user.getEmail(), user);
      String msg = "";
      this.sendEmail(user, msg);
    }
  }

  public User autenticateUser(String email, String senha) {
    User user = new User();
    try {
      if (this.users.get(email).getSenha().equals(senha)) {
        user = this.users.get(email);
      }
    } catch (RuntimeException e){   }

    return user;
  }


  private void sendEmail(User user, String message) {



  }


}
