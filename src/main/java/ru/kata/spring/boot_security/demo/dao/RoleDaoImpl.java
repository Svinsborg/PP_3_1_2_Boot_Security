package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class RoleDaoImpl implements RoleDao{


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role findRole(String role) {
        TypedQuery<Role> roles = entityManager.createQuery("FROM Role r WHERE r.role=:role", Role.class);
        roles.setParameter("role", role);
        return roles.getSingleResult();
    }

    @Override
    public Role findRoleByID(Long id){
        Role role = entityManager.find(Role.class, id);
        return role;
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }
}
