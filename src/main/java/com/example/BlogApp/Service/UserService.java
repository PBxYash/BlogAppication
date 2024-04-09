package com.example.BlogApp.Service;



import com.example.BlogApp.Entity.User;
import com.example.BlogApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public interface UserService {
    public User save(User user);

    public User findByUsernameOrEmail(String usernameOrEmail);
    public User findByUsername(String username);

    public User FindByUserID(long id );

}
