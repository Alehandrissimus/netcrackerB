package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;

import java.util.Collection;

public interface AnnouncementService {
    Collection<Announcement> getAllAnnouncements();

    void validateLikedUser(User user, Announcement announcement);

    void validateAnnouncement(Announcement announcement);

    void buildNewAnnouncement(String title, String description,String address, String owner);
}
