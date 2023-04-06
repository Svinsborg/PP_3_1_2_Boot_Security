package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserDaoEMImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    private final RoleDao roleDao;
    @Autowired
    public UserDaoEMImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public User findById(Long id) {
        User user = entityManager.find(User.class, id) ;
        return user;
    }

    public Optional<User> findByUserName(String name){
        Query jpqlQuery = (Query) entityManager.createQuery("FROM User u WHERE u.firstName=:name", User.class);
        jpqlQuery.setParameter("name", name);
        Optional<User> first = jpqlQuery.getResultList().stream().findFirst();
        System.out.println("Repo Optional repport  --- >>>>  " + first);
        return first;
    }

    @Override
    public void createUser(User user){
        Set<Role> role = new HashSet<>();
        role.add(roleDao.findRole("USER"));
        user.setRoles(role);
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void saveUser(String firstName, String lastName, String password) {
        Set<Role> role = new HashSet<>();
        role.add(roleDao.findRole("USER"));
        System.out.println("Find user role ---->>>>> " + role);
        User user = new User(firstName,lastName, password, role);
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user){
        System.out.println("Method updateUser report ----- >> for user = " + user);
        if(user.getRoles().isEmpty()){
            Set<Role> role = new HashSet<>();
            role.add(roleDao.findRole("USER"));
            user.setRoles(role);
        }
        entityManager.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        entityManager.remove(user);
    }
}
