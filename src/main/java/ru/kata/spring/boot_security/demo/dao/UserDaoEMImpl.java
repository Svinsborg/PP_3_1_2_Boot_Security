package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        TypedQuery<User> user = entityManager.createQuery("FROM User u WHERE u.id=:id", User.class);
        user.setParameter("id", id);
        return user.getResultList().stream().findAny().orElse(null);
    }

    public Optional<User> findByUserName(String name){
        Query jpqlQuery = (Query) entityManager.createQuery("FROM User u WHERE u.firstName=:name", User.class);
        jpqlQuery.setParameter("name", name);
        Optional first = jpqlQuery.getResultList().stream().findFirst();
        System.out.println("Repo Optional repport  --- >>>>  " + first);
        return first;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    @Transactional
    public void saveUser(String firstName, String lastName, String password) {
        Set<Role> role = new HashSet<>();
        role.add(roleDao.findRole("USER"));
        System.out.println("Find user role ---->>>>> " + role);
        User user = new User(firstName,lastName, password, role);
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(User user){
        System.out.println("Method updateUser report ----- >> for user = " + user);
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = findById(id);
        entityManager.remove(user);
    }
}
