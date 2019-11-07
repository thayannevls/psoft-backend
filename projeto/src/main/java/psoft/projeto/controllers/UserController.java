package psoft.projeto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psoft.projeto.entities.User;
import psoft.projeto.services.JWTService;
import psoft.projeto.services.UserServices;

@RestController


@RequestMapping("/api/v1")
public class UserController {

  private UserServices userService;
  private JWTService jwtService;

  public UserController(UserServices userService, JWTService jwtService){
    this.jwtService = jwtService;
    this.userService = userService;
  }


  @PostMapping("/user")
  public ResponseEntity<User> addUser(@RequestBody User user){
    return new ResponseEntity<User> (this.userService.insertUser(user), HttpStatus.OK);
  }


}
