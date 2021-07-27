package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExist;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAnnouncementDAOImplTest {

    private static final String TEST_TITLE = "test";
    private static final String TEST_DESCRIPTION = "testDescription";
    private static final String TEST_ADDRESS = "testAddress";
    private static final String LOG_ERROR = "Error while setting test connection" + " ";

    private UserAnnouncementDAOImpl userAnnouncementDAO;
    private AnnouncementDAOImpl announcementDAO;
    private UserDAOImpl userDAO;

    private static final Logger log = Logger.getLogger(UserAnnouncementDAOImpl.class);

    @Autowired
    private void setUserAnnouncementDAO(UserAnnouncementDAOImpl userAnnouncementDAO) {
        this.userAnnouncementDAO = userAnnouncementDAO;
        try {
            userAnnouncementDAO.setTestConnection();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.error(LOG_ERROR + e.getMessage());
        }
    }

    @Autowired
    private void setAnnouncementDAO(AnnouncementDAOImpl announcementDAO) {
        this.announcementDAO = announcementDAO;
        try {
            announcementDAO.setTestConnection();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.error(LOG_ERROR + e.getMessage());
        }
    }

    @Autowired
    private void setUserDAO(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
        try {
            userDAO.setTestConnection();
        } catch (DAOConfigException e) {
            log.error(LOG_ERROR + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getAnnouncementsLikedByUser() {
        try {
            Set<Announcement> announcementSet = userAnnouncementDAO.getAnnouncementsLikedByUser(BigInteger.ONE);
            assertNotNull(announcementSet);
            for(Announcement announcement : announcementSet)
                assertNotNull(announcement);

        } catch (AnnouncementDoesNotExist | DAOLogicException e) {
            log.error("Error while testing getAnnouncementsLikedByUser " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getUsersLikedAnnouncement() {
        try {
            Set<User> userSet = userAnnouncementDAO.getUsersLikedAnnouncement(BigInteger.ONE);
            assertNotNull(userSet);
            for(User user : userSet)
                assertNotNull(user);
        } catch (UserDoesNotExistException | DAOLogicException e) {
            log.error("Error while testing getUsersLikedAnnouncement " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void addAndGetParticipantById() {

        try {
            BigInteger idAnnouncement = announcementDAO.createAnnouncement(new AnnouncementImpl.AnnouncementBuilder()
                    .setTitle(TEST_TITLE)
                    .setDescription(TEST_DESCRIPTION)
                    .setOwner(BigInteger.ONE)
                    .setDate(new Date())
                    .setAddress(TEST_ADDRESS)
                    .setParticipantsCap(5)
                    .build()
            );
            userDAO.createUser(
                    new UserImpl.UserBuilder()
                            .setFirstName("testFirstName")
                            .setLastName("testLastName")
                            .setDescription("testDescription")
                            .setEmail("test@gmail.com")
                            .setPassword("testPassword")
                            .setEmailCode("testEmailCode")
                            .build()
            );

            assertNotNull(announcementDAO.getAnnouncementById(idAnnouncement));

            User user = userDAO.getUserByEmail("test@gmail.com");
            assertNotNull(user);

            userAnnouncementDAO.addParticipant(idAnnouncement, user.getId());

            assertTrue(userAnnouncementDAO.getParticipantById(idAnnouncement, user.getId()));

            announcementDAO.deleteAnnouncement(idAnnouncement);
            userDAO.deleteUser(user.getId());

        } catch (DAOLogicException | AnnouncementDoesNotExist | UserDoesNotExistException e) {
            log.error("Error while testing addAndGetParticipantById" + e.getMessage());
            fail();
        }
    }
}