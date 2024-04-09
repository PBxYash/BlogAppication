package com.example.BlogApp.Service;



import com.example.BlogApp.Entity.Role;
import com.example.BlogApp.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface RoleService {



    public Role save(Role role);

    public Role findByName(String name);
}

