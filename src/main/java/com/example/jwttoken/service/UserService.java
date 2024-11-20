package com.example.jwttoken.service;

import com.example.jwttoken.exception.UserNotFoundException;
import com.example.jwttoken.repository.UserRepository;
import com.example.jwttoken.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(String.valueOf(id));
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserByLogin(String login) {
        return findAll().stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }

}

