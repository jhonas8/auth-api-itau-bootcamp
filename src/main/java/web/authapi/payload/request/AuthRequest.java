package web.authapi.payload.request;

public class AuthRequest {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
