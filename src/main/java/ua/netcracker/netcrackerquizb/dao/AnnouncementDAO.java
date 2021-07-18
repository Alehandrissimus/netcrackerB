package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AnnouncementDAO {
    void deleteAnnouncement(Announcement announcement);

    Announcement editAnnouncement(Announcement announcement, Map<String, String> mapAnnouncement);

    void addParticipant(Announcement announcement, User user);

    List<Announcement> getPopular();

    Collection<Announcement> getLikedByUser(User user);

    void getParticipantById(Announcement announcement, User user);

    Announcement getByTitle(String title);

    Announcement getPopular(BigInteger number);

}
