package vodagone.application.util;

public class Validation {
    public boolean checkToken(String token) {
        return (token != null && !token.isEmpty());
    }

    public boolean checkId(int id) {
        return (id != 0);
    }
}
