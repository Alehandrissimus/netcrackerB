package ua.netcracker.netcrackerquizb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import ua.netcracker.netcrackerquizb.dao.impl.UserDAOImpl;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;

public class DAOUtil {
//
//  private static final String PATH_PROPERTY = "src/main/resources/sqlScripts.properties";
//  private static final String DRIVER_PATH_PROPERTY = "oracle.jdbc.OracleDriver";
//
//  private static final Logger log = Logger.getLogger(DAOUtil.class);
//
//  private DAOUtil() {
//  }
//
//  public static Connection setTestConnection()
//      throws DAOConfigException {
//    return getDataSource(URL, USERNAME + "_TEST", PASSWORD, properties, true);
//  }
//
//  public static Connection getDataSource(String URL, String USERNAME, String PASSWORD, Properties properties, boolean test)
//      throws  DAOConfigException {
//
//    try (FileInputStream fis = new FileInputStream(PATH_PROPERTY)) {
//      Class.forName(DRIVER_PATH_PROPERTY);
//      properties.load(fis);
//
//      return test? DriverManager.getConnection(URL, USERNAME, PASSWORD);
//    } catch (Exception e){
//      log.error("DAO config error " + e.getMessage());
//      throw new DAOConfigException();
//    }
//  }

}
