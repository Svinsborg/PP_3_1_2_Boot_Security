package ru.kata.spring.boot_security.demo.dto;

import javax.persistence.Column;

public class RoleDTO {
    private Long id;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
