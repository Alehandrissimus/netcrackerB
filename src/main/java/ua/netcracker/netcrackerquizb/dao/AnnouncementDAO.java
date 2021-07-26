package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExist;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface AnnouncementDAO {


    String PATH_PROPERTY = "src/main/resources/sqlScripts.properties";
    String DRIVER_PATH_PROPERTY = "oracle.jdbc.OracleDriver";
    String URL_PROPERTY = "${spring.datasource.url}";
    String USERNAME_PROPERTY = "${spring.datasource.username}";
    String PASSWORD_PROPERTY = "${spring.datasource.password}";

    String SELECT_ANNOUNCEMENT_BY_TITLE = "SELECT_ANNOUNCEMENT_BY_TITLE";
    String SELECT_SET_ANNOUNCEMENT_BY_TITLE = "SELECT_SET_ANNOUNCEMENT_BY_TITLE";
    String CREATE_ANNOUNCEMENT = "CREATE_ANNOUNCEMENT";
    String UPDATE_ANNOUNCEMENT = "UPDATE_ANNOUNCEMENT";
    String DELETE_ANNOUNCEMENT_BY_ID = "DELETE_ANNOUNCEMENT_BY_ID";
    String GET_POPULAR_ANNOUNCEMENT = "GET_POPULAR_ANNOUNCEMENT";
    String GET_ANNOUNCEMENT_BY_ID = "GET_ANNOUNCEMENT_BY_ID";

    String ID_ANNOUNCEMENT = "ID_ANNOUNCEMENT";
    String TITLE = "TITLE";
    String DESCRIPTION = "DESCRIPTION";
    String OWNER = "OWNR";
    String DATE_CREATE = "DATE_CREATE";
    String ADDRESS = "ADDRESS";
    String LIKES = "LIKES";

    String DAO_LOGIC_EXCEPTION = "Dao logic exception ";
    String ANNOUNCEMENT_NOT_FOUND_EXCEPTION = "Announcement does not exist!";
    String ANNOUNCEMENT_HAS_NOT_BEEN_RECEIVED = "Announcement has not been received";

    Announcement getAnnouncementById(BigInteger idAnnouncement) throws AnnouncementDoesNotExist, DAOLogicException;

    void deleteAnnouncement(BigInteger idAnnouncement) throws DAOLogicException;

    BigInteger createAnnouncement(Announcement newAnnouncement) throws DAOLogicException;

    void editAnnouncement(Announcement newAnnouncement) throws DAOLogicException;

    Announcement getByTitle(String title) throws AnnouncementDoesNotExist, DAOLogicException;

    Set<Announcement> getSetByTitle(String title) throws AnnouncementDoesNotExist, DAOLogicException;

    List<Announcement> getPopular(int number) throws AnnouncementDoesNotExist, DAOLogicException;

}
