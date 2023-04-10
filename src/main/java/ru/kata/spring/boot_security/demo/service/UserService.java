package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findById(Long id);
    List<User> getAllUsers();

    void createUser(User user);

    void updateUser(User user);

    void deleteById(Long id);

    Optional<User> findByUserName(String name);

}
