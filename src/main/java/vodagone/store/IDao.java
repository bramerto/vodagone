package vodagone.store;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDao {
    ResultSet getAll() throws SQLException;
}
