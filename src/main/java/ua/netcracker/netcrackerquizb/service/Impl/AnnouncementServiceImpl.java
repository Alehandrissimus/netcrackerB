package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.impl.AnnouncementDAOImpl;
import ua.netcracker.netcrackerquizb.exception.AnnouncementException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import ua.netcracker.netcrackerquizb.service.AnnouncementService;
import java.math.BigInteger;
import java.util.Collection;

import static ua.netcracker.netcrackerquizb.exception.MessagesForException.*;


@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private static final Logger log = Logger.getLogger(AnnouncementServiceImpl.class);

    @Autowired
    AnnouncementDAOImpl announcementDAO;

    @Override
    public Collection<Announcement> getAllAnnouncements() {
        return null;
    }

    @Override
    public void validateLikedUser(User user, Announcement announcement) {
    }

    @Override
    public void validateAnnouncement(Announcement announcement) {
    }

    @Override
    public BigInteger buildNewAnnouncement(String title , String description, String address, BigInteger owner) throws AnnouncementException, DAOLogicException {
        title = title.trim();
        description = description.trim();
        address = address.trim();
        try {
            if(announcementDAO.isAnnouncementByTitle(title)) {
                log.info(ANNOUNCEMENT_ALREADY_EXISTS + " in buildNewAnnouncement()");
                throw new AnnouncementException(ANNOUNCEMENT_ALREADY_EXISTS);
            }
            return announcementDAO.createAnnouncement(new AnnouncementImpl.AnnouncementBuilder()
                    .setTitle(title)
                    .setDescription(description)
                    .setAddress(address)
                    .setOwner(owner)
                    .build());
        } catch (DAOLogicException e) {
            log.info(DAO_LOGIC_EXCEPTION + " in buildNewAnnouncement()");
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
        }
    }
}
