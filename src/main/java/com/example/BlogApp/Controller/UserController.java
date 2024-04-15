package com.example.BlogApp.Controller;

import com.example.BlogApp.Entity.User;
import com.example.BlogApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    private static final String UPLOAD_DIR = "src/main/resources/static/user-images/";

    @GetMapping("/{Userid}")
    public ResponseEntity<User>getUser(@PathVariable long Userid){
        User user = userService.FindByUserID(Userid);
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/get/{userId}")
    public ResponseEntity<byte[]> getImageByUserId(@PathVariable Long userId) {
        User userImage = userService.FindByUserID(userId);
        if (userImage != null) {
            try {
                String imagePath = UPLOAD_DIR + userImage.getImagePath();
                Path imageFilePath = Paths.get(imagePath);
                byte[] imageData = Files.readAllBytes(imageFilePath);
                return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageData);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
