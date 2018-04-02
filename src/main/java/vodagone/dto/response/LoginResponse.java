package vodagone.dto.response;

public class LoginResponse implements IResponse {
    private String token;
    private String user;

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}