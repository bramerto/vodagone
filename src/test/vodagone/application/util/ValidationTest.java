package vodagone.application.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationTest {

    @Test
    public void checkTokenIsNotEmpty() {
        Validation validation = new Validation();
        String token = "token";

        boolean checked = validation.checkToken(token);

        assertTrue(checked);
    }


    @Test
    public void checkTokenIsEmpty() {
        Validation validation = new Validation();
        String token = "";

        boolean checked = validation.checkToken(token);

        assertFalse(checked);
    }

    @Test
    public void checkTokenIsNull() {
        Validation validation = new Validation();

        boolean checked = validation.checkToken(null);

        assertFalse(checked);
    }

    @Test
    public void checkIdIsNotEmpty() {
        Validation validation = new Validation();
        int id = 1;

        boolean checked = validation.checkId(id);

        assertTrue(checked);
    }


    @Test
    public void checkIdIsEmpty() {
        Validation validation = new Validation();
        int id = 0;

        boolean checked = validation.checkId(id);

        assertFalse(checked);
    }
}
