package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.AnnouncementException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface UserAnnouncementDAO {

    String SELECT_USERS_LIKED_ANNOUNCEMENT = "SELECT_USERS_LIKED_ANNOUNCEMENT";
    String SELECT_ANNOUNCEMENT_LIKED_BY_USER = "SELECT_ANNOUNCEMENT_LIKED_BY_USER";
    String GET_PARTICIPANT_BY_ID = "GET_PARTICIPANT_BY_ID";
    String ADD_PARTICIPANT = "ADD_PARTICIPANT";
    String DELETE_PARTICIPANT = "DELETE_PARTICIPANT";
    String SELECT_ALL_ANNOUNCEMENT = "SELECT_ALL_ANNOUNCEMENT";


    String USER_ID = "USER_ID";
    String USER_FIRST_NAME = "USER_FIRST_NAME";
    String USER_LAST_NAME = "USER_LAST_NAME";
    String USER_EMAIL = "USER_EMAIL";
    String USER_PASSWORD = "USER_PASSWORD";
    String USER_ROLE = "USER_ROLE";
    String USER_ACTIVE = "USER_ACTIVE";
    String USER_EMAIL_CODE = "USER_EMAIL_CODE";
    String USER_DESCRIPTION = "USER_DESCRIPTION";

    String ERROR_TEST_CONNECTION = "Error while setting test connection ";

    String MESSAGE_FOR_GET_ANNOUNCEMENTS_LIKED_BY_USER = " in getAnnouncementsLikedByUser";
    String MESSAGE_FOR_GET_USERS_LIKED_ANNOUNCEMENT = " in getUsersLikedAnnouncement";
    String MESSAGE_FOR_GET_ALL_ANNOUNCEMENTS = " in getAllAnnouncement";

    Set<Announcement> getAnnouncementsLikedByUser(BigInteger idUser) throws AnnouncementDoesNotExistException, DAOLogicException, AnnouncementException;

    Set<User> getUsersLikedAnnouncement(BigInteger idAnnouncement) throws UserDoesNotExistException, DAOLogicException;

    boolean isParticipant(BigInteger idAnnouncement, BigInteger idUser) throws DAOLogicException;

    void addParticipant(BigInteger idAnnouncement, BigInteger idUser) throws DAOLogicException;

    void deleteParticipant(BigInteger idAnnouncement, BigInteger idUser) throws DAOLogicException;

    List<Announcement> getAllAnnouncements(BigInteger idUser)
            throws AnnouncementDoesNotExistException, DAOLogicException, AnnouncementException;

}
