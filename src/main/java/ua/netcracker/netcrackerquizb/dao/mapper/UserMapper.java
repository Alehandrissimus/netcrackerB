package ua.netcracker.netcrackerquizb.dao.mapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.UserRoles;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

public class UserMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet resultSet, int i) throws SQLException {
    User user = new UserImpl();
    user.setId((BigInteger) resultSet.getObject("id"));
    user.setFirstName(resultSet.getString("first_name"));
    user.setLastName(resultSet.getString("last_name"));
    user.setEmail(resultSet.getString("email"));
    user.setPassword(resultSet.getString("passwd"));
    user.setRole((UserRoles) resultSet.getObject("usr_role"));
//    user.setAccomplishedQuizes();
//    user.setFavoriteQuizes();
    user.setEmailCode(resultSet.getString("code"));
    return user;
  }
}
