package ua.netcracker.netcrackerquizb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import ua.netcracker.netcrackerquizb.dao.impl.UserDAOImpl;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;

public class DAOUtil {

  private static final String PROPERTY = "sqlScripts.properties";
  private static final String DRIVER_PATH_PROPERTY = "oracle.jdbc.OracleDriver";

  private static final Logger log = Logger.getLogger(DAOUtil.class);

  private DAOUtil() {
  }

  public static Connection getDataSource(String URL, String USERNAME, String PASSWORD, Properties properties)
      throws  DAOConfigException {

    try (InputStream fis = DAOUtil.class.getClassLoader().getResourceAsStream(PROPERTY)) {
      Class.forName(DRIVER_PATH_PROPERTY);
      properties.load(fis);

      return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    } catch (Exception e){
      log.error("DAO config error " + e.getMessage());
      throw new DAOConfigException("Dao config exception ",e);
    }
  }

}
