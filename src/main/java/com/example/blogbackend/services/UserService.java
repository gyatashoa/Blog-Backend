package com.example.blogbackend.services;

import com.example.blogbackend.models.AppUser;
import com.example.blogbackend.models.AuthRequest;
import com.example.blogbackend.repositories.UserRepository;
import com.example.blogbackend.utils.BcryptUtil;
import com.example.blogbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     return this.userRepository.findByEmail(email).
             orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    public ResponseEntity<?> addUser(AppUser user){

        try{
            var temp = this.userRepository.findByEmail(user.getEmail());
            if(!temp.isEmpty()) throw new RuntimeException("Email already registered");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            var msgObject = new HashMap<>();
            msgObject.put("Error",e.getMessage());
            return ResponseEntity.badRequest().body(msgObject);
        }
    }

    public ResponseEntity<?> signIn(AuthRequest authRequest){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())
            );

        }catch (BadCredentialsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        AppUser user =  (AppUser) loadUserByUsername(authRequest.getEmail());
        String token = jwtUtil.generateToken(user);
        var body = new HashMap<>();
        body.put("jwt",token);
        return ResponseEntity.ok()
                .body(body);
    }
}
