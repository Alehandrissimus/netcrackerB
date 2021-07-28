package ua.netcracker.netcrackerquizb.service.Impl;

import com.fasterxml.jackson.databind.introspect.Annotated;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.dao.impl.AnnouncementDAOImpl;
import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.AnnouncementException;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.model.Announcement;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnnouncementServiceImplTest {

    private static final String LOG_ERROR = "Error while setting test connection" + " ";


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
            BigInteger idAnnouncement = announcementService.buildNewAnnouncement("test", "testDescription" ,
                    "testAddress" , BigInteger.ONE);
            assertNotNull(idAnnouncement);
            AnnouncementException exception = assertThrows(AnnouncementException.class,
                    ()-> announcementService.buildNewAnnouncement("test", "testDescription" ,
                            "testAddress" , BigInteger.ONE));
            assertNotNull(exception);
            announcementDAO.deleteAnnouncement(idAnnouncement);
        } catch (AnnouncementException | DAOLogicException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

    }
}