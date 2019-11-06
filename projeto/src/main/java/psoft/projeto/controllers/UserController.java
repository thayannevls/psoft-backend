package psoft.projeto.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController


@RequestMapping("/api/v1")
public class UserController {

  @Autowired
  private UserServices service;

/*

  @PostMapping("/user")
  public ResponseEntity<User> addUser(@RequestBody User user){
    return new ResponseEntity<User> (service.insertUser(user), HttpStatus.OK);
  }

*/
}
