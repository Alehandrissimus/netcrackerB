package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExist;
import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
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

    Announcement getAnnouncementById(BigInteger idAnnouncement) throws AnnouncementDoesNotExist, DaoLogicException;

    void deleteAnnouncement(BigInteger idAnnouncement) throws DaoLogicException;

    BigInteger createAnnouncement(Announcement newAnnouncement) throws DaoLogicException;

    void editAnnouncement(Announcement newAnnouncement) throws DaoLogicException;

    Announcement getByTitle(String title) throws AnnouncementDoesNotExist, DaoLogicException;

    Set<Announcement> getSetByTitle(String title) throws AnnouncementDoesNotExist, DaoLogicException;

    List<Announcement> getPopular(int number) throws AnnouncementDoesNotExist, DaoLogicException;

}
