package com.dbonnin.mobile_backend.controllers;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbonnin.mobile_backend.persistence.User;
import com.dbonnin.mobile_backend.persistence.UserRepository;

import lombok.AllArgsConstructor;



@AllArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("users/{id}")
    public User getUser(@RequestParam("id") Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @GetMapping("users")
    public List<User> getUser() {
        return userRepository.findAll();
    }   
    
    @PostMapping("users")
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PutMapping("users/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {

        User _user=userRepository.findById(id).orElseThrow();
        if(_user!=null){
            _user.setEmail(user.getEmail());
            _user.setFirstName(user.getFirstName());
            _user.setLastName(user.getLastName());
            _user.setUsername(user.getUsername());
            _user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(_user);
        }
        
        return _user;

    }

    @DeleteMapping("users/{id}")
    public User deleteUser(@PathVariable("id") Long id) {
        User _user=userRepository.findById(id).orElseThrow();
        if(_user!=null){
            userRepository.delete(_user);
        }
        return _user;
    }

    @GetMapping("users/login/{username}")
    public User login(@PathVariable("usernane") String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    

}
