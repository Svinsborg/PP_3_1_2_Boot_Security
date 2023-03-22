package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dto.DtoUserAuthorization;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.Optional;


@Controller
public class UserController {

    private final UserService userService;
    private final RoleDao roleDao;


    @Autowired
    public UserController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }



    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping(value = {"/", "/index", "/users", "/user"})
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DtoUserAuthorization ua = (DtoUserAuthorization) authentication.getPrincipal();
        System.out.println("############### User Details printing ---- >>>>>> " + ua.getUsername() + "   ################");
        Optional<User> user = userService.findByUserName(ua.getUsername());
        System.out.println("############### User Details printing ---- >>>>>> " + user + "   ################");
        model.addAttribute("user", user.get());
        return "/user";
    }


    @GetMapping("/create")
    public String createUser() {
        return "create";
    }

    @PostMapping("/create")
    public String saveUser(@RequestParam("firstName") String name,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("password") String password,
                           Model model) {
        if (name == "" && lastName == "" && password =="") {
            model.addAttribute("msg", "All fields must be filled!");
            return "create";
        }
        Optional<User> user = userService.findByUserName(name);
        if (user.isPresent()) {
            model.addAttribute("msg", "The user already exists!");
            return "create";
        } else {
            userService.saveUser(name, lastName, password);
            return "redirect:/login";
        }
    }
}
