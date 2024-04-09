package com.example.BlogApp.Service.impl;

import com.example.BlogApp.Entity.Role;
import com.example.BlogApp.Repository.RoleRepository;
import com.example.BlogApp.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService
{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}

