package com.example.BlogApp.Controller;

import com.example.BlogApp.Entity.User;
import com.example.BlogApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{Userid}")
    public ResponseEntity<User>getUser(@PathVariable long Userid){
        User user = userService.FindByUserID(Userid);
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }
}
