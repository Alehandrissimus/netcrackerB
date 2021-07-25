package ua.netcracker.netcrackerquizb.dao;

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
    String CREATE_ANNOUNCEMENT = "CREATE_ANNOUNCEMENT";
    String UPDATE_ANNOUNCEMENT = "UPDATE_ANNOUNCEMENT";
    String ADD_PARTICIPANT = "ADD_PARTICIPANT";
    String DELETE_ANNOUNCEMENT_BY_ID = "DELETE_ANNOUNCEMENT_BY_ID";
    String GET_POPULAR_ANNOUNCEMENT = "GET_POPULAR_ANNOUNCEMENT";
    String SELECT_ANNOUNCEMENT_LIKED_BY_USER = "SELECT_ANNOUNCEMENT_LIKED_BY_USER";
    String GET_PARTICIPANT_BY_ID = "GET_PARTICIPANT_BY_ID";

    String ID_ANNOUNCEMENT = "ID_ANNOUNCEMENT";
    String TITLE = "TITLE";
    String DESCRIPTION = "DESCRIPTION";
    String OWNER = "OWNR";
    String DATE_CREATE = "DATE_CREATE";
    String ADDRESS = "ADDRESS";
    String LIKES = "LIKES";


    void deleteAnnouncement(BigInteger idAnnouncement);

    void createAnnouncement(Announcement newAnnouncement);

    void editAnnouncement(Announcement newAnnouncement);

    void addParticipant(BigInteger idAnnouncement, BigInteger idUser);

    boolean getParticipantById(BigInteger idAnnouncement, BigInteger idUser);

    Announcement getByTitle(String title);

    Set<Announcement> getAnnouncementsLikedByUser(BigInteger idUser);

    List<Announcement> getPopular(int number);

}
