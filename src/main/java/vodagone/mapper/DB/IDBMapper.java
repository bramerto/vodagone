package vodagone.mapper.DB;

import vodagone.domain.IMappable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDBMapper {
    ArrayList getList(ResultSet rs) throws SQLException;
    IMappable getSingle(ResultSet rs) throws SQLException;
    IMappable map(ResultSet rs) throws SQLException;
}
