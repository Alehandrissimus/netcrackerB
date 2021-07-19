package ua.netcracker.netcrackerquizb.service.Impl;

import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.service.AnnouncementService;

import java.util.Collection;

public class AnnouncementServiceImpl implements AnnouncementService {
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
    public void buildNewAnnouncement(String title, String description, String address, String owner) {

    }
}
