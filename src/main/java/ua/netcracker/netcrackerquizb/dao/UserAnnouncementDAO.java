package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExist;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;
import java.math.BigInteger;
import java.util.Set;

public interface UserAnnouncementDAO {

    String SELECT_USERS_LIKED_ANNOUNCEMENT = "SELECT_USERS_LIKED_ANNOUNCEMENT";
    String SELECT_ANNOUNCEMENT_LIKED_BY_USER = "SELECT_ANNOUNCEMENT_LIKED_BY_USER";
    String GET_PARTICIPANT_BY_ID = "GET_PARTICIPANT_BY_ID";
    String ADD_PARTICIPANT = "ADD_PARTICIPANT";

    String USER_ID = "USER_ID";
    String USER_FIRST_NAME = "USER_FIRST_NAME";
    String USER_LAST_NAME = "USER_LAST_NAME";
    String USER_EMAIL = "USER_EMAIL";
    String USER_PASSWORD = "USER_PASSWORD";
    String USER_ROLE = "USER_ROLE";
    String USER_ACTIVE = "USER_ACTIVE";
    String USER_EMAIL_CODE = "USER_EMAIL_CODE";
    String USER_DESCRIPTION = "USER_DESCRIPTION";

    Set<Announcement> getAnnouncementsLikedByUser(BigInteger idUser) throws AnnouncementDoesNotExist, DAOLogicException;

    Set<User> getUsersLikedAnnouncement(BigInteger idAnnouncement) throws UserDoesNotExistException, DAOLogicException;

    boolean getParticipantById(BigInteger idAnnouncement, BigInteger idUser) throws DAOLogicException;

    void addParticipant(BigInteger idAnnouncement, BigInteger idUser) throws DAOLogicException;

}
