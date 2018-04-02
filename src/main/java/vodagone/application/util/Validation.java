package vodagone.application.util;

public class Validation {
    public boolean checkToken(String token) {
        return (token != null && !token.isEmpty());
    }
}
