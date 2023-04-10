package ru.kata.spring.boot_security.demo.dto.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;



@Component
public class Converter {

    private final ModelMapper modelMapper;

    @Autowired
    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToUser(UserDTO user) {
        return modelMapper.map(user, User.class);
    }

    public RoleDTO convertToRoleDTO(Role role){
        return modelMapper.map(role, RoleDTO.class);
    }
}
