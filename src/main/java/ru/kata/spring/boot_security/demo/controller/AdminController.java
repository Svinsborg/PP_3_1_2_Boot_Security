package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = {"/", "/index", ""})
    public String administration(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User us = (User) authentication.getPrincipal();
        Optional<User> user = userService.findByUserName(us.getUsername());
        model.addAttribute("account", user.get());


        model.addAttribute("usersList", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.findAll());
        return "/admin/index";
    }

    @GetMapping("/users/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        if (id >= 0) {
            User user = userService.findById(id);
            if (user != null) {
                model.addAttribute("users", user);
                return "/admin/index";
            } else {
                model.addAttribute("msg", "User not found");
                return "/err/warning";
            }
        } else {
            model.addAttribute("msg", "Invalid request");
            return "/err/warning";
        }
    }


    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            model.addAttribute("msg", "User not found");
            return "err/warning";
        } else {
            userService.deleteById(id);
            return "redirect:/admin";
        }
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("user") User user) {
        System.out.println(user);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/create")
    public String saveUser(@RequestParam("firstName") String name,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("password") String password,
                           Model model) {
        if (name == "" && lastName == "" && password =="") {
            model.addAttribute("msg", "All fields must be filled!");
            return "/err/warning";
        }
        Optional<User> user = userService.findByUserName(name);
        if (user.isPresent()) {
            model.addAttribute("msg", "The user already exists!");
            return "/err/warning";
        } else {
            userService.saveUser(name, lastName, password);
            return "redirect:/admin/index";
        }
    }
}
