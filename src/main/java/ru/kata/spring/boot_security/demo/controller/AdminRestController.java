package ru.kata.spring.boot_security.demo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.excption.UserErrors;
import ru.kata.spring.boot_security.demo.excption.UserNotCreateException;
import ru.kata.spring.boot_security.demo.excption.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/roles/{id}")
    public RoleDTO getRoleByID(@PathVariable Long id) {
        return convertToRoleDTO(roleService.findRoleByID(id));
    }

    @GetMapping("/roles")
    public List<RoleDTO> getAllRole() {
        return roleService.findAll().stream()
                .map(this::convertToRoleDTO)
                .toList();
    }


    @GetMapping()
    public List<UserDTO> getAll() {
        return userService.getAllUsers().stream()
                .map(this::convertToUserDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") Long id) {
        return convertToUserDTO(userService.findById(id));
    }

    @GetMapping("/name/{name}")
    public Boolean checkByName(@PathVariable("name") String name) {
        Optional<User> user = userService.findByUserName(name);
        return user.isPresent();
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> userCreate(@Valid @RequestBody UserDTO userDTO,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError err : errors) {
                errMsg.append(err.getField())
                        .append(" - ")
                        .append(err.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreateException(errMsg.toString());
        }
        userService.createUser(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.findById(id);
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PatchMapping("/")
    public ResponseEntity<HttpStatus> update(@Valid @RequestBody UserDTO userDTO) {
        System.out.println("PATCH Json Object ------- >>>>>>>>> " + userDTO);
        userService.findById(userDTO.getId());
        System.out.println("Converter print ------>>>>>>  " + convertToUser(userDTO));
        userService.updateUser(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO user) {
        return modelMapper.map(user, User.class);
    }

    private RoleDTO convertToRoleDTO(Role role){
        return modelMapper.map(role, RoleDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrors> errorsHandlerException(UserNotFoundException e) {
        UserErrors userErrors = new UserErrors(
                "User not found! " + e,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(userErrors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrors> errorsHandlerException(UserNotCreateException e) {
        UserErrors userErrors = new UserErrors(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(userErrors, HttpStatus.BAD_REQUEST);
    }
}
