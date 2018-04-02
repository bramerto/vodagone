package vodagone.service;

import vodagone.domain.User;
import vodagone.dto.request.LoginRequest;
import vodagone.dto.response.LoginResponse;
import vodagone.mapper.DB.UserDBMapper;
import vodagone.mapper.UserResponseMapper;
import vodagone.store.UserDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/login")
public class LoginService {

    private UserDao userDao = new UserDao();
    private UserResponseMapper userResponseMapper = new UserResponseMapper();
    private UserDBMapper userDBMapper = new UserDBMapper();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {

        try {
            ResultSet RSuser = userDao.getUserByLogin(request.getUser(), request.getPassword());
            User user = userDBMapper.getSingle(RSuser);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            LoginResponse response = userResponseMapper.mapToResponse(user);

            return Response.status(Response.Status.CREATED).entity(response).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
