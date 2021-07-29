package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.AnnouncementException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.exception.UserException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface AnnouncementService {

    List<Announcement> getAllAnnouncements(BigInteger idUser)
            throws AnnouncementDoesNotExistException, DAOLogicException, AnnouncementException;

    BigInteger buildNewAnnouncement(Announcement announcement)
            throws AnnouncementException, DAOLogicException, UserException;

    void editAnnouncement(Announcement announcement, BigInteger idUser)
            throws AnnouncementException, DAOLogicException;

    void deleteAnnouncement(BigInteger idAnnouncement, BigInteger idUser)
            throws DAOLogicException, AnnouncementException;

    void toLikeAnnouncement(BigInteger idAnnouncement, BigInteger idUser)
            throws AnnouncementException, DAOLogicException;

    void toDisLikeAnnouncement(BigInteger idAnnouncement, BigInteger idUser)
            throws AnnouncementException, DAOLogicException;

    List<Announcement> getPopularAnnouncements(int numberAnnouncements)
            throws AnnouncementDoesNotExistException, DAOLogicException;

    Set<Announcement> getAnnouncementsLikedByUser(BigInteger idUser)
            throws AnnouncementDoesNotExistException, DAOLogicException, AnnouncementException;
}
