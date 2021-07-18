package ua.netcracker.netcrackerquizb.dao.impl;

import ua.netcracker.netcrackerquizb.dao.AnnouncementDAO;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AnnouncementDAOImpl implements AnnouncementDAO {

    @Override
    public void deleteAnnouncement(Announcement announcement) {

    }

    @Override
    public Announcement editAnnouncement(Announcement announcement, Map<String, String> mapAnnouncement) {
        return null;
    }

    @Override
    public void addParticipant(Announcement announcement, User user) {

    }

    @Override
    public List<Announcement> getPopular() {
        return null;
    }

    @Override
    public Collection<Announcement> getLikedByUser(User user) {
        return null;
    }

    @Override
    public void getParticipantById(Announcement announcement, User user) {

    }

    @Override
    public Announcement getByTitle(String title) {
        return null;
    }

    @Override
    public Announcement getPopular(BigInteger number) {
        return null;
    }
}
