package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.dao.impl.AnnouncementDAOImpl;
import ua.netcracker.netcrackerquizb.dao.impl.UserAnnouncementDAOImpl;
import ua.netcracker.netcrackerquizb.dao.impl.UserDAOImpl;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.UserRoles;
import ua.netcracker.netcrackerquizb.service.AnnouncementService;
import java.math.BigInteger;
import java.util.List;

import static ua.netcracker.netcrackerquizb.exception.MessagesForException.*;


@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private static final Logger log = Logger.getLogger(AnnouncementServiceImpl.class);

    @Autowired
    AnnouncementDAOImpl announcementDAO;
    @Autowired
    UserAnnouncementDAOImpl userAnnouncementDAO;
    @Autowired
    UserDAOImpl userDAO;

    @Override
    public List<Announcement> getAllAnnouncements(BigInteger idUser)
            throws AnnouncementDoesNotExistException, DAOLogicException, AnnouncementException {
        return userAnnouncementDAO.getAnnouncements(idUser);
    }

    @Override
    public BigInteger buildNewAnnouncement(Announcement announcement)
            throws UserException, AnnouncementException, DAOLogicException {

        try {
            if(!userDAO.getUserById(announcement.getOwner()).getUserRole().equals(UserRoles.ADMIN))
                throw new UserException(DONT_ENOUGH_RIGHTS);

            if(announcementDAO.isAnnouncementByTitle(announcement.getTitle())) {
                log.error(ANNOUNCEMENT_ALREADY_EXISTS + " in buildNewAnnouncement()");
                throw new AnnouncementException(ANNOUNCEMENT_ALREADY_EXISTS);
            }
            return announcementDAO.createAnnouncement(announcement);
        } catch (DAOLogicException | UserDoesNotExistException e) {
            log.error(DAO_LOGIC_EXCEPTION + " in buildNewAnnouncement()");
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
        }
    }

    @Override
    public void editAnnouncement(Announcement announcement, BigInteger idUser)
            throws AnnouncementException, DAOLogicException {
        try {
            if(!userDAO.getUserById(idUser).getUserRole().equals(UserRoles.ADMIN))
                throw new AnnouncementException(DONT_ENOUGH_RIGHTS);
            announcementDAO.editAnnouncement(announcement);
        } catch (DAOLogicException | UserDoesNotExistException e) {
            log.error(DAO_LOGIC_EXCEPTION + " in editAnnouncement()");
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
        }
    }

    @Override
    public void deleteAnnouncement(BigInteger idAnnouncement, BigInteger idUser)
            throws DAOLogicException, AnnouncementException {
        try {
            if(!userDAO.getUserById(idUser).getUserRole().equals(UserRoles.ADMIN))
                throw new AnnouncementException(DONT_ENOUGH_RIGHTS);
            announcementDAO.deleteAnnouncement(idAnnouncement);
        } catch (UserDoesNotExistException e) {
            log.error(DAO_LOGIC_EXCEPTION + " in deleteAnnouncement()");
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
        }
    }

    @Override
    public void toLikeAnnouncement(BigInteger idAnnouncement, BigInteger idUser)
            throws AnnouncementException, DAOLogicException {
        try {
            if(userAnnouncementDAO.isParticipant(idAnnouncement, idUser))
                throw new AnnouncementException(ANNOUNCEMENT_ALREADY_LIKED);
            userAnnouncementDAO.addParticipant(idAnnouncement, idUser);
            announcementDAO.toLike(idAnnouncement);
        } catch (DAOLogicException e) {
            log.error(DAO_LOGIC_EXCEPTION + " in toLikeAnnouncement()");
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
        }
    }

    @Override
    public void toDisLikeAnnouncement(BigInteger idAnnouncement, BigInteger idUser)
            throws AnnouncementException, DAOLogicException {
        try {
            if(!userAnnouncementDAO.isParticipant(idAnnouncement, idUser))
                throw new AnnouncementException(ANNOUNCEMENT_HAS_NOT_LIKED);
            userAnnouncementDAO.deleteParticipant(idAnnouncement, idUser);
            announcementDAO.toDisLike(idAnnouncement);
        } catch (DAOLogicException e) {
            log.error(DAO_LOGIC_EXCEPTION + " in toDisLikeAnnouncement()");
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION, e);
        }
    }
}
