package com.users.service.services;

import com.users.service.entity.Admin;
import com.users.service.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AdminService implements UserDetailsService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin;
        try {
            admin = adminRepository.findByUsername(username);
        }
        catch(Exception e) {
            throw new UsernameNotFoundException("Admin not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        admin.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(),authorities);
    }

}
