package ua.netcracker.netcrackerquizb.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AnnouncementDAOImplTest {

    @Autowired
    private AnnouncementDAOImpl announcementDAO;

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getPopular() {

        List<Announcement> popularAnnouncement = announcementDAO.getPopular(1);
        assertNotNull(popularAnnouncement);
        assertNotNull(popularAnnouncement.get(0));
        assertEquals(1, popularAnnouncement.size());
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getLikedByUser() {

        Set<Announcement> announcementSet;
        announcementSet = announcementDAO.getLikedByUser(BigInteger.valueOf(1));
        assertNotNull(announcementSet);
        for(Announcement announcement : announcementSet)
            assertNotNull(announcement);
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getByTitle() {

        AnnouncementImpl newAnnouncement = new AnnouncementImpl();
        newAnnouncement.setTitle("test");
        newAnnouncement.setDescription("testDescription");
        newAnnouncement.setOwner(BigInteger.ONE);
        newAnnouncement.setDate(new Date());
        newAnnouncement.setAddress("testAddress");
        newAnnouncement.setParticipantsCap(5);
        announcementDAO.createAnnouncement(newAnnouncement);

        AnnouncementImpl announcement = announcementDAO.getByTitle("test");
        assertNotNull(announcement);
        assertEquals("test", announcement.getTitle());

        announcementDAO.deleteAnnouncement(announcement.getId());
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void createAnnouncement() {

        AnnouncementImpl newAnnouncement = new AnnouncementImpl();
        newAnnouncement.setTitle("test");
        newAnnouncement.setDescription("testDescription");
        newAnnouncement.setOwner(BigInteger.ONE);
        newAnnouncement.setDate(new Date());
        newAnnouncement.setAddress("testAddress");
        newAnnouncement.setParticipantsCap(5);
        announcementDAO.createAnnouncement(newAnnouncement);

        AnnouncementImpl testAnnouncement = announcementDAO.getByTitle("test");
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

        AnnouncementImpl newAnnouncement = new AnnouncementImpl();
        newAnnouncement.setTitle("test");
        newAnnouncement.setDescription("testDescription");
        newAnnouncement.setOwner(BigInteger.ONE);
        newAnnouncement.setDate(new Date());
        newAnnouncement.setAddress("testAddress");
        newAnnouncement.setParticipantsCap(5);
        announcementDAO.createAnnouncement(newAnnouncement);

        AnnouncementImpl announcement = announcementDAO.getByTitle("test");
        assertNotNull(announcement);

        announcement.setTitle("newTest");
        announcement.setDescription("newDescription");
        announcement.setAddress("newAddress");
        announcementDAO.editAnnouncement(announcement);

        AnnouncementImpl testAnnouncement = announcementDAO.getByTitle("newTest");
        assertEquals(announcement.getTitle(), testAnnouncement.getTitle());
        assertEquals(announcement.getDescription(), testAnnouncement.getDescription());
        assertEquals(announcement.getAddress(), testAnnouncement.getAddress());

        announcementDAO.deleteAnnouncement(testAnnouncement.getId());
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void deleteAnnouncement() {

        AnnouncementImpl newAnnouncement = new AnnouncementImpl();
        newAnnouncement.setTitle("test");
        newAnnouncement.setDescription("testDescription");
        newAnnouncement.setOwner(BigInteger.ONE);
        newAnnouncement.setDate(new Date());
        newAnnouncement.setAddress("testAddress");
        newAnnouncement.setParticipantsCap(5);
        announcementDAO.createAnnouncement(newAnnouncement);

        AnnouncementImpl testAnnouncement = announcementDAO.getByTitle("test");
        assertNotNull(testAnnouncement);

        announcementDAO.deleteAnnouncement(testAnnouncement.getId());
        assertNull(announcementDAO.getByTitle("test"));
    }
}