package ru.kata.spring.boot_security.demo.dto;



import ru.kata.spring.boot_security.demo.model.Role;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Long id = 0L;
    @NotBlank(message = "Name not empty!")
    @Size(min = 3, max = 15, message = "Name 3 do 15 char")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Pass not empty!")
    @Size(min = 3, max = 15, message = "Pass 3 do 15 char")
    private String password;
    private Set<Role> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
