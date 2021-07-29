package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface AnnouncementDAO {

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
    String SET_LIKE = "SET_LIKE";
    String UNSET_LIKE = "UNSET_LIKE";

    String ID_ANNOUNCEMENT = "ID_ANNOUNCEMENT";
    String TITLE = "TITLE";
    String DESCRIPTION = "DESCRIPTION";
    String OWNER = "OWNR";
    String DATE_CREATE = "DATE_CREATE";
    String ADDRESS = "ADDRESS";
    String LIKES = "LIKES";

    String ERROR_TEST_CONNECTION = "Error while setting test connection ";

    Announcement getAnnouncementById(BigInteger idAnnouncement) throws AnnouncementDoesNotExistException, DAOLogicException;

    void deleteAnnouncement(BigInteger idAnnouncement) throws DAOLogicException;

    BigInteger createAnnouncement(Announcement newAnnouncement) throws DAOLogicException;

    void editAnnouncement(Announcement newAnnouncement) throws DAOLogicException;

    Announcement getByTitle(String title) throws AnnouncementDoesNotExistException, DAOLogicException;

    Set<Announcement> getSetByTitle(String title) throws AnnouncementDoesNotExistException, DAOLogicException;

    List<Announcement> getPopular(int number) throws AnnouncementDoesNotExistException, DAOLogicException;

    boolean isAnnouncementByTitle(String title) throws DAOLogicException;

    void toLike(BigInteger idAnnouncement) throws DAOLogicException;

    void toDisLike(BigInteger idAnnouncement) throws DAOLogicException;
}
