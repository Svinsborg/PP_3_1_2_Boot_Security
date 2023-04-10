package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserDaoEMImpl;
import ru.kata.spring.boot_security.demo.excption.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service()
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImp(UserDaoEMImpl userDaoImp, RoleDao roleDao) {
        this.userDao = userDaoImp;
        this.roleDao = roleDao;
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
    public void createUser(User user){
        Set<Role> role = new HashSet<>();
        user.getRoles().forEach(e -> role.add(roleDao.findRole(String.valueOf(e))));
        user.setRoles(null);
        user.setRoles(role);
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
        UserDetails ud = new User(user.get());
        return ud;
    }
}


