package ru.kata.spring.boot_security.demo.dao;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User findById(Long id);

    Optional<User> findByUserName(String name);


    List<User> getAllUsers();

    void saveUser(String firstName, String lastName, String password);

    void updateUser(User user);

    void deleteById(Long id);

    void createUser(User user);

}
