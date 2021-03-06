package ru.job4j.chat.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class PersonDTO {
    @NotNull(message = "Id must be non null")
    private int id;
    @NotBlank(message = "Username must be not empty")
    private String username;
    @NotBlank(message = "Password must be not empty")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonDTO personDTO = (PersonDTO) o;
        return id == personDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PersonDTO{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
