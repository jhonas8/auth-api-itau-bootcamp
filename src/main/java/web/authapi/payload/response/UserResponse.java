package web.authapi.payload.response;

import org.springframework.beans.factory.annotation.Autowired;
import web.authapi.repository.UserRepository;

import java.util.List;

public class UserResponse {
    private Long id;
    private List<String> roles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    @Autowired
    private UserRepository repository;

    public UserResponse(
            Long id,
            List<String> roles,
            String token
    ) {
        this.id = id;
        this.roles = roles;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
