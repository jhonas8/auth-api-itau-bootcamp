package web.authapi.models;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BaseUser {

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    protected String email;

    @NotBlank
    @Size(min=3, max = 15)
    @Column(nullable = false, unique = true)
    protected String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max=20)
    private String firstName;

    @NotBlank
    @Size(max=20)
    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
