package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


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
        model.addAttribute("users", userService.getAllUsers());
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

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            model.addAttribute("msg", "User not found");
            return "err/warning";
        } else {
            List<Role> roles = roleService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("allRoles", roles);
            return "admin/update";
        }
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("user") User user) {
        System.out.println(user);
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
