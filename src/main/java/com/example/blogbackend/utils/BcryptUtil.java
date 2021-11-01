package com.example.blogbackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptUtil {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String hashPassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

}
