package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDaoEMImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findById(Long id) {
        User user = entityManager.find(User.class, id) ;
        return user;
    }

    public Optional<User> findByUserName(String name){
        Query jpqlQuery = (Query) entityManager.createQuery("FROM User u WHERE u.firstName=:name", User.class);
        jpqlQuery.setParameter("name", name);
        Optional<User> first = jpqlQuery.getResultList().stream().findFirst();
        return first;
    }

    @Override
    public void createUser(User user){
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void updateUser(User user){
        entityManager.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        entityManager.remove(user);
    }
}
