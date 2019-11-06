package psoft.projeto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psoft.projeto.services.UserServices;

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
