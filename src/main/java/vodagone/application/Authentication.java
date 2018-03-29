package vodagone.application;

import vodagone.domain.User;
import vodagone.store.UserDao;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

public class Authentication {

    @Inject
    private UserDao userDao;

    public Response.Status checkUserByToken(String token) throws SQLException {
        if (token == null) {
            return Response.Status.BAD_REQUEST;
        }

        User user = userDao.getUserByToken(token);

        if (user == null) {
            return Response.Status.UNAUTHORIZED;
        }
        return Response.Status.OK;
    }
}