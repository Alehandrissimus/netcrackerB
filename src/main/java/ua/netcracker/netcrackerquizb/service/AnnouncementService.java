package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.AnnouncementException;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;

import java.math.BigInteger;
import java.util.Collection;

public interface AnnouncementService {

    Collection<Announcement> getAllAnnouncements();

    void validateLikedUser(User user, Announcement announcement);

    void validateAnnouncement(Announcement announcement);

    BigInteger buildNewAnnouncement(String title, String description, String address, BigInteger owner) throws AnnouncementException, DAOLogicException;
}
