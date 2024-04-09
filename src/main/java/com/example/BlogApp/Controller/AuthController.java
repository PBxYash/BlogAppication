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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if user already exists
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Set default role for new user (e.g., ROLE_USER)
        Role defaultRole = roleService.findByName("ROLE_USER");
        if (defaultRole == null) {
            throw new RuntimeException("Default role not found");
        }
        user.setRoles(Set.of(defaultRole));

        // Save the user
        User save = userService.save(user);

        return new ResponseEntity<>(save, HttpStatus.CREATED);
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
