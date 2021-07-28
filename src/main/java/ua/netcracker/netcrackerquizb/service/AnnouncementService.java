package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExistException;
import ua.netcracker.netcrackerquizb.exception.AnnouncementException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface AnnouncementService {

    List<Announcement> getAllAnnouncements(BigInteger idUser)
            throws AnnouncementDoesNotExistException, DAOLogicException, AnnouncementException;

    void validateLikedUser(User user, Announcement announcement);

    void validateAnnouncement(Announcement announcement);

    BigInteger buildNewAnnouncement(String title, String description, String address, BigInteger owner)
            throws AnnouncementException, DAOLogicException;
}
