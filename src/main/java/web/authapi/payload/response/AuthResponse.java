package web.authapi.payload.response;

import web.authapi.payload.request.AuthRequest;

import java.util.List;

public class AuthResponse {
    public String getErrorMessage() {
        return errorMessage;
    }

    public AuthResponse(
            boolean isValid,
            Long id,
            List<String> roles
    ) {
        this.authenticated = isValid;

        if(!isValid){
            this.errorMessage = "Error validating token";
        }

        this.roles = roles;
        this.id = id;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    private String errorMessage;
    private boolean authenticated;

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

    private Long id;
    private List<String> roles;
}
