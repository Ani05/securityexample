package com.example.securityexample.security;

import com.example.securityexample.model.User;
import com.example.securityexample.repositori.UserRepasitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepasitory userRepasitory;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User byEmail = userRepasitory.findByEmail(s);
        if (byEmail==null){
            throw  new UsernameNotFoundException("user whit"+ s+ "does not exists");
        }
        return new SpringUser(byEmail);
    }
}
