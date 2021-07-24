package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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



    private AnnouncementDAOImpl announcementDAO;
    private static final Logger log = Logger.getLogger(UserDAOImplTest.class);

    @Autowired
    private void setUserDAO(AnnouncementDAOImpl announcementDAO) {
        this.announcementDAO = announcementDAO;
        try {
            announcementDAO.setTestConnection();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            log.error("Error while setting test connection " + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getPopular() {

        List<Announcement> popularAnnouncement = announcementDAO.getPopular(4);
        assertNotNull(popularAnnouncement);
        for(Announcement announcement : popularAnnouncement)
            assertNotNull(announcement);
        assertEquals(4, popularAnnouncement.size());
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getLikedByUser() {

        Set<Announcement> announcementSet;
        announcementSet = announcementDAO.getAnnouncementsLikedByUser(BigInteger.valueOf(1));
        assertNotNull(announcementSet);
        for(Announcement announcement : announcementSet)
            assertNotNull(announcement);
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getByTitle() {


        announcementDAO.createAnnouncement(new AnnouncementImpl.AnnouncementBuilder()
                .setTitle("test")
                .setDescription("testDescription")
                .setOwner(BigInteger.ONE)
                .setDate(new Date())
                .setAddress("testAddress")
                .setParticipantsCap(5)
                .build());

        Announcement announcement = announcementDAO.getByTitle("test");
        assertNotNull(announcement);
        assertEquals("test", announcement.getTitle());

        announcementDAO.deleteAnnouncement(announcement.getId());
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getByNullTitle() {
        assertNull(announcementDAO.getByTitle(""));
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void createAnnouncement() {

        assertNull(announcementDAO.getByTitle("test"));

        Announcement newAnnouncement = new AnnouncementImpl.AnnouncementBuilder()
                .setTitle("test")
                .setDescription("testDescription")
                .setOwner(BigInteger.ONE)
                .setDate(new Date())
                .setAddress("testAddress")
                .setParticipantsCap(5)
                .build();

        announcementDAO.createAnnouncement(newAnnouncement);

        Announcement testAnnouncement = announcementDAO.getByTitle("test");
        assertNotNull(testAnnouncement);
        assertEquals(newAnnouncement.getTitle(), testAnnouncement.getTitle());
        assertEquals(newAnnouncement.getDescription(), testAnnouncement.getDescription());
        assertEquals(newAnnouncement.getOwner(), testAnnouncement.getOwner());
        assertEquals(newAnnouncement.getAddress(), testAnnouncement.getAddress());
        assertEquals(newAnnouncement.getParticipantsCap(), testAnnouncement.getParticipantsCap());

        announcementDAO.deleteAnnouncement(testAnnouncement.getId());
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void editAnnouncement() {

        Announcement newAnnouncement = new AnnouncementImpl.AnnouncementBuilder()
                .setTitle("test")
                .setDescription("testDescription")
                .setOwner(BigInteger.ONE)
                .setDate(new Date())
                .setAddress("testAddress")
                .setParticipantsCap(5)
                .build();

        announcementDAO.createAnnouncement(newAnnouncement);

        Announcement announcement = announcementDAO.getByTitle("test");
        assertNotNull(announcement);

        announcement.setTitle("newTest");
        announcement.setDescription("newDescription");
        announcement.setAddress("newAddress");
        announcementDAO.editAnnouncement(announcement);

        Announcement testAnnouncement = announcementDAO.getByTitle("newTest");
        assertEquals(announcement.getTitle(), testAnnouncement.getTitle());
        assertEquals(announcement.getDescription(), testAnnouncement.getDescription());
        assertEquals(announcement.getAddress(), testAnnouncement.getAddress());

        announcementDAO.deleteAnnouncement(testAnnouncement.getId());
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void deleteAnnouncement() {

        Announcement newAnnouncement = new AnnouncementImpl.AnnouncementBuilder()
                .setTitle("test")
                .setDescription("testDescription")
                .setOwner(BigInteger.ONE)
                .setDate(new Date())
                .setAddress("testAddress")
                .setParticipantsCap(5)
                .build();
        announcementDAO.createAnnouncement(newAnnouncement);

        Announcement testAnnouncement = announcementDAO.getByTitle("test");
        assertNotNull(testAnnouncement);

        announcementDAO.deleteAnnouncement(testAnnouncement.getId());
        assertNull(announcementDAO.getByTitle("test"));
    }

//    @Test
//    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
//    void getParticipantById() {
//
//        Announcement newAnnouncement = new AnnouncementImpl();
//        newAnnouncement.setTitle("test");
//        newAnnouncement.setDescription("testDescription");
//        newAnnouncement.setOwner(BigInteger.ONE);
//        newAnnouncement.setDate(new Date());
//        newAnnouncement.setAddress("testAddress");
//        newAnnouncement.setParticipantsCap(5);
//        announcementDAO.createAnnouncement(newAnnouncement);
//
//        userDAO.createUser("test@gmail.com", "testLastName", "testFirstName", "testPassword", "testCode");
//
//        Announcement testAnnouncement = announcementDAO.getByTitle("test");
//        assertNotNull(testAnnouncement);
//
//        User testUser = userDAO.getUserByEmail("test@gmail.com");
//        assertNotNull(testUser);
//
//        announcementDAO.addParticipant(testAnnouncement.getId(), testUser.getId());
//
//        assertTrue(announcementDAO.getParticipantById(testAnnouncement.getId(), testUser.getId()));
//
//        announcementDAO.deleteAnnouncement(testAnnouncement.getId());
//        userDAO.deleteUser(testUser.getId());
//    }
}