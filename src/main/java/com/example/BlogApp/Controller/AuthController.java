package com.example.BlogApp.Controller;

import com.example.BlogApp.DTO.JwtResponse;
import com.example.BlogApp.DTO.LoginDto;
import com.example.BlogApp.Entity.Role;
import com.example.BlogApp.Entity.User;
import com.example.BlogApp.Service.RoleService;
import com.example.BlogApp.Service.UserService;
import com.example.BlogApp.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RoleService roleService; // Autowire RoleService

    private static final String UPLOAD_DIR = "src/main/resources/static/user-images/";
//    @PostMapping("/register")
//    @CrossOrigin(origins = "http://localhost:4200")
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        // Check if user already exists
//        if (userService.findByUsername(user.getUsername()) != null) {
//            return ResponseEntity.badRequest().body("Username already exists");
//        }
//
//        // Set default role for new user (e.g., ROLE_USER)
//        Role defaultRole = roleService.findByName("ROLE_USER");
//        if (defaultRole == null) {
//            throw new RuntimeException("Default role not found");
//        }
//        user.setRoles(Set.of(defaultRole));
//
//        // Save the user
//        User save = userService.save(user);
//
//        return new ResponseEntity<>(save, HttpStatus.CREATED);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam("name") String name,
                                          @RequestParam("username") String username,
                                          @RequestParam("email") String email,
                                          @RequestParam("password") String password,
                                          @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Check if user already exists
            if (userService.findByUsername(username) != null) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            // Create new user
            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            // Set default role for new user (e.g., ROLE_USER)
            Role defaultRole = roleService.findByName("ROLE_USER");
            if (defaultRole == null) {
                throw new RuntimeException("Default role not found");
            }
            user.setRoles(Set.of(defaultRole));

            // Set image path and save the image
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            Path uploadDir = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath);
            user.setImagePath(fileName);

            // Save the user
            User savedUser = userService.save(user);

            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload user information and image");
        }
    }
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        try {
            // Perform authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

            // If authentication is successful, generate JWT token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);
            User byUsername = userService.findByUsername(loginDto.getUsernameOrEmail());
            long id = byUsername.getId();
            return ResponseEntity.ok().body(new JwtResponse(id,"Login Success", true, jwt));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
