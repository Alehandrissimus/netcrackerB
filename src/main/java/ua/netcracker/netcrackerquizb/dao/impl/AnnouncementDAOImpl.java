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
    public void deleteAnnouncement(AnnouncementImpl announcement) {

    }

    @Override
    public AnnouncementImpl editAnnouncement(AnnouncementImpl announcement, Map<String, String> mapAnnouncement) {
        return null;
    }

    @Override
    public void addParticipant(AnnouncementImpl announcement, UserImpl user) {

    }

    @Override
    public List<AnnouncementImpl> getPopular() {
        return null;
    }

    @Override
    public Collection<AnnouncementImpl> getLikedByUser(UserImpl user) {
        return null;
    }

    @Override
    public void getParticipantById(AnnouncementImpl announcement, UserImpl user) {

    }

    @Override
    public AnnouncementImpl getByTitle(String title) {
        return null;
    }

    @Override
    public AnnouncementImpl getPopular(BigInteger number) {
        return null;
    }
}
