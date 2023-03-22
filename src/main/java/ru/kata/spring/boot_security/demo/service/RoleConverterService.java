package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

@Component
public class RoleConverterService implements Converter<String, Role> {

    private final RoleDao roleDao;

    @Autowired
    public RoleConverterService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role convert(String id) {
        System.out.println("########## Converter debug monitor for id = " + id + " in to roles ##########" );
        Long parse = Long.parseLong(id);
        Role role =roleDao.findRoleByID(parse);
        System.out.println("########## Converter debug monitor for id = " + id + " find role = " + role + "  ##########" );
        return role;
    }
}
