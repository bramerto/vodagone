package vodagone.dto.request;

public class LoginRequest implements IRequest {
    private String user;
    private String password;

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
