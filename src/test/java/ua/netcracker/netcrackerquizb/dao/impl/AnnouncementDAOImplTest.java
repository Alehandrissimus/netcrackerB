package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExist;
import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AnnouncementDAOImplTest {

    private static final String TEST_TITLE = "test";
    private static final String TEST_DESCRIPTION = "testDescription";
    private static final String TEST_ADDRESS = "testAddress";
    private static final String TEST_NEW_TITLE = "newTest";
    private static final String TEST_NEW_DESCRIPTION = "newDescription";
    private static final String TEST_NEW_ADDRESS = "newAddress";
    private static final String LOG_ERROR = "Error while setting test connection" + " ";

    private AnnouncementDAOImpl announcementDAO;

    private static final Logger log = Logger.getLogger(AnnouncementDAOImplTest.class);

    @Autowired
    private void setAnnouncementDAO(AnnouncementDAOImpl announcementDAO) {
        this.announcementDAO = announcementDAO;
        try {
            announcementDAO.setTestConnection();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.error(LOG_ERROR + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getPopular() {
        try {
            List<Announcement> popularAnnouncement = announcementDAO.getPopular(4);
            assertNotNull(popularAnnouncement);
            for(Announcement announcement : popularAnnouncement)
                assertNotNull(announcement);
            assertEquals(4, popularAnnouncement.size());
        } catch (AnnouncementDoesNotExist | DaoLogicException e) {
            log.error("Error while testing getPopular" + e.getMessage());
            fail();
        }
    }


    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getByTitle() {
        try {
            announcementDAO.createAnnouncement(new AnnouncementImpl.AnnouncementBuilder()
                    .setTitle(TEST_TITLE)
                    .setDescription(TEST_DESCRIPTION)
                    .setOwner(BigInteger.ONE)
                    .setDate(new Date())
                    .setAddress(TEST_ADDRESS)
                    .setParticipantsCap(5)
                    .build());
            System.out.println("created");
            Announcement announcement = announcementDAO.getByTitle(TEST_TITLE);
            System.out.println("geted by title");
            assertNotNull(announcement);
            assertEquals(TEST_TITLE, announcement.getTitle());
            announcementDAO.deleteAnnouncement(announcement.getId());
            System.out.println("deleted");
        } catch (AnnouncementDoesNotExist | DaoLogicException e) {
            log.error("Error while testing getByTitle " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void createAnnouncement() {
        Announcement newAnnouncement = new AnnouncementImpl.AnnouncementBuilder()
                .setTitle(TEST_TITLE)
                .setDescription(TEST_DESCRIPTION)
                .setOwner(BigInteger.ONE)
                .setDate(new Date())
                .setAddress(TEST_ADDRESS)
                .setParticipantsCap(5)
                .build();
        try {
            BigInteger idAnnouncement = announcementDAO.createAnnouncement(newAnnouncement);
            assertTrue(idAnnouncement.intValue() > 0);
            announcementDAO.deleteAnnouncement(idAnnouncement);
        } catch (DaoLogicException e) {
            log.error("Error while testing createAnnouncement " + e.getMessage());
            fail();
        }
    }


    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void editAnnouncement() {
        Announcement newAnnouncement = new AnnouncementImpl.AnnouncementBuilder()
                .setTitle(TEST_TITLE)
                .setDescription(TEST_DESCRIPTION)
                .setOwner(BigInteger.ONE)
                .setDate(new Date())
                .setAddress(TEST_ADDRESS)
                .setParticipantsCap(5)
                .build();
        try {
            BigInteger idAnnouncement = announcementDAO.createAnnouncement(newAnnouncement);
            Announcement announcement = announcementDAO.getAnnouncementById(idAnnouncement);
            assertNotNull(announcement);
            announcement.setTitle(TEST_NEW_TITLE);
            announcement.setDescription(TEST_NEW_DESCRIPTION);
            announcement.setAddress(TEST_NEW_ADDRESS);
            announcementDAO.editAnnouncement(announcement);
            Announcement testAnnouncement = announcementDAO.getAnnouncementById(idAnnouncement);
            assertEquals(announcement.getTitle(), testAnnouncement.getTitle());
            assertEquals(announcement.getDescription(), testAnnouncement.getDescription());
            assertEquals(announcement.getAddress(), testAnnouncement.getAddress());
            announcementDAO.deleteAnnouncement(idAnnouncement);
        } catch (DaoLogicException | AnnouncementDoesNotExist e) {
            log.error("Error while testing editAnnouncement " + e.getMessage());
            fail();
        }
    }

    @Test()
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    @ExceptionHandler()
    void deleteAnnouncement() {

        Announcement newAnnouncement = new AnnouncementImpl.AnnouncementBuilder()
                .setTitle(TEST_TITLE)
                .setDescription(TEST_DESCRIPTION)
                .setOwner(BigInteger.ONE)
                .setDate(new Date())
                .setAddress(TEST_ADDRESS)
                .setParticipantsCap(5)
                .build();
        try {
            BigInteger idAnnouncement = announcementDAO.createAnnouncement(newAnnouncement);
            assertNotNull(announcementDAO.getAnnouncementById(idAnnouncement));
            announcementDAO.deleteAnnouncement(idAnnouncement);
            AnnouncementDoesNotExist thrown = assertThrows(AnnouncementDoesNotExist.class, () ->
                announcementDAO.getAnnouncementById(idAnnouncement));
            assertNotNull(thrown);
        } catch (DaoLogicException | AnnouncementDoesNotExist e) {
            e.printStackTrace();
            log.error("Error while testing deleteAnnouncement " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getAnnouncementById() {
        try {
            assertNotNull(announcementDAO.getAnnouncementById(BigInteger.ONE));
        } catch (AnnouncementDoesNotExist | DaoLogicException e) {
            log.error("Error while testing getAnnouncementById " + e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getSetByTitle() {
        try {
            Set<Announcement> announcements = announcementDAO.getSetByTitle("gath");
            assertNotNull(announcements);
            for(Announcement announcement : announcements){
                assertNotNull(announcement);
            }
        } catch (AnnouncementDoesNotExist | DaoLogicException e) {
            log.error("Error while testing getSetByTitle " + e.getMessage());
            fail();
        }
    }
}