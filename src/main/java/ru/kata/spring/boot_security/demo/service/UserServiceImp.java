package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserDaoEMImpl;
import ru.kata.spring.boot_security.demo.excption.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;


@Service()
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImp(UserDaoEMImpl userDaoImp) {
        this.userDao = userDaoImp;
    }

    @Override
    public User findById(Long id) {
        User user = userDao.findById(id);
        if (user != null) {
            return user;
        } else {
            throw  new UserNotFoundException();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void saveUser(String firstName, String lastName, String password) {
        userDao.saveUser(firstName, lastName, password);
    }

    @Override
    @Transactional
    public void createUser(User user){
        userDao.createUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user){
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public Optional<User> findByUserName(String name) {
        return userDao.findByUserName(name);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userDao.findByUserName(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found by name = " + username);
        }
        System.out.println("Service repport --- >>>>  " + user);
        UserDetails ud = new User(user.get());
        System.out.println("########## Get Authorities ------ >>>>>> " + ud.getAuthorities().toString());
        return ud;
    }
}































