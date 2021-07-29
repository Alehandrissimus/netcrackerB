package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.dao.impl.AnnouncementDAOImpl;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnnouncementServiceImplTest {

    private static final String TEST_TITLE = "test";
    private static final String TEST_DESCRIPTION = "testDescription";
    private static final String TEST_ADDRESS = "testAddress";
    private static final String TEST_NEW_TITLE = "newTest";
    private static final String TEST_NEW_DESCRIPTION = "newDescription";
    private static final String TEST_NEW_ADDRESS = "newAddress";
    private static final String LOG_ERROR = "Error while setting test connection" + " ";
    private static final String LOG_ERROR_CASE = "Error while testing" + " ";


    @Autowired
    AnnouncementServiceImpl announcementService;
    AnnouncementDAOImpl announcementDAO;
    private static final Logger log = Logger.getLogger(AnnouncementServiceImpl.class);

    @Autowired
    private void setAnnouncementDAO(AnnouncementDAOImpl announcementDAO) {
        this.announcementDAO = announcementDAO;
        try {
            announcementDAO.setTestConnection();
        } catch (DAOConfigException e) {
            log.error(LOG_ERROR + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void buildNewAnnouncement() {
        try {

            BigInteger idAnnouncement = announcementService.buildNewAnnouncement(new AnnouncementImpl.AnnouncementBuilder()
                    .setTitle(TEST_TITLE)
                    .setDescription(TEST_DESCRIPTION)
                    .setOwner(BigInteger.ONE)
                    .setAddress(TEST_ADDRESS)
                    .build());
            assertNotNull(idAnnouncement);
            AnnouncementException exception = assertThrows(AnnouncementException.class,
                    ()-> announcementService.buildNewAnnouncement(new AnnouncementImpl.AnnouncementBuilder()
                            .setTitle(TEST_TITLE)
                            .setDescription(TEST_DESCRIPTION)
                            .setOwner(BigInteger.ONE)
                            .setAddress(TEST_ADDRESS)
                            .build()));
            assertNotNull(exception);
            announcementService.deleteAnnouncement(idAnnouncement, BigInteger.ONE);
        } catch (AnnouncementException | DAOLogicException | UserException e) {
            log.error(LOG_ERROR_CASE +"buildNewAnnouncement "+ e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getAllAnnouncements() {
        try {
            List<Announcement> announcementList = announcementService.getAllAnnouncements(BigInteger.ONE);
            assertNotNull(announcementList);
            for(Announcement announcement: announcementList){
                assertNotNull(announcement);
            }
        } catch (AnnouncementDoesNotExistException | DAOLogicException | AnnouncementException e) {
            log.error(LOG_ERROR_CASE +"getAllAnnouncements "+ e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void editAnnouncement() {
        try {
            BigInteger idAnnouncement = announcementService.buildNewAnnouncement(new AnnouncementImpl.AnnouncementBuilder()
                    .setTitle(TEST_TITLE)
                    .setDescription(TEST_DESCRIPTION)
                    .setOwner(BigInteger.ONE)
                    .setAddress(TEST_ADDRESS)
                    .build());
            assertNotNull(idAnnouncement);
            announcementService.editAnnouncement(new AnnouncementImpl.AnnouncementBuilder()
                    .setId(idAnnouncement)
                    .setTitle(TEST_NEW_TITLE)
                    .setDescription(TEST_NEW_DESCRIPTION)
                    .setAddress(TEST_NEW_ADDRESS)
                    .build(), BigInteger.ONE);
            Announcement testAnnouncement = announcementDAO.getAnnouncementById(idAnnouncement);
            assertEquals(TEST_NEW_TITLE, testAnnouncement.getTitle());
            assertEquals(TEST_NEW_DESCRIPTION, testAnnouncement.getDescription());
            assertEquals(TEST_NEW_ADDRESS, testAnnouncement.getAddress());
            announcementService.deleteAnnouncement(idAnnouncement, BigInteger.ONE);
        } catch (DAOLogicException | AnnouncementException | AnnouncementDoesNotExistException | UserException e) {
            log.error(LOG_ERROR_CASE +"editAnnouncement "+ e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void deleteAnnouncement() {
        try {
            BigInteger idAnnouncement = announcementService.buildNewAnnouncement(new AnnouncementImpl.AnnouncementBuilder()
                    .setTitle(TEST_TITLE)
                    .setDescription(TEST_DESCRIPTION)
                    .setOwner(BigInteger.ONE)
                    .setAddress(TEST_ADDRESS)
                    .build());
            assertNotNull(idAnnouncement);
            announcementService.deleteAnnouncement(idAnnouncement, BigInteger.ONE);
            AnnouncementDoesNotExistException thrown = assertThrows(AnnouncementDoesNotExistException.class, () ->
                    announcementDAO.getAnnouncementById(idAnnouncement));
            assertNotNull(thrown);
        } catch (DAOLogicException | AnnouncementException | UserException e) {
            log.error(LOG_ERROR_CASE +"deleteAnnouncement "+ e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void toLikeAndDisLikeAnnouncement() {
        try {
            announcementService.toLikeAnnouncement(BigInteger.ONE, BigInteger.TWO);
            assertThrows(AnnouncementException.class, () ->
                    announcementService.toLikeAnnouncement(BigInteger.ONE, BigInteger.ONE));
            announcementService.toDisLikeAnnouncement(BigInteger.ONE, BigInteger.TWO);

        } catch (AnnouncementException | DAOLogicException e) {
            log.error(LOG_ERROR_CASE +"toLikeAndDisLikeAnnouncement "+ e.getMessage());
            fail();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getPopularAnnouncements() {
        try {
            List<Announcement> announcements = announcementService.getPopularAnnouncements(4);
            assertNotNull(announcements);
            for(Announcement announcement: announcements)
                assertNotNull(announcement);
        } catch (AnnouncementDoesNotExistException | DAOLogicException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Timeout(value = 10000, unit= TimeUnit.MILLISECONDS)
    void getAnnouncementsLikedByUser() {
        try {
            Set<Announcement> announcementSet = announcementService.getAnnouncementsLikedByUser(BigInteger.ONE);
            assertNotNull(announcementSet);
            for(Announcement announcement: announcementSet)
                assertNotNull(announcement);
        } catch (AnnouncementDoesNotExistException | DAOLogicException | AnnouncementException e) {
            e.printStackTrace();
        }
    }
}