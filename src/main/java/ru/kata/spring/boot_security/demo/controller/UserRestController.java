package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.excption.UserErrors;
import ru.kata.spring.boot_security.demo.excption.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;
import java.util.Optional;




@RestController
@RequestMapping("/api/v2/user")
public class UserRestController {


    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/viewUser")
    public ResponseEntity<User> showUser(Principal principal) {
        Optional<User> user = userService.findByUserName(principal.getName());
        return ResponseEntity.ok(user.get());
    }

    @ExceptionHandler
    private ResponseEntity<UserErrors> errorsHandlerException(UserNotFoundException e) {
        UserErrors userErrors = new UserErrors(
                "User not found! " + e,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(userErrors, HttpStatus.NOT_FOUND);
    }
}
