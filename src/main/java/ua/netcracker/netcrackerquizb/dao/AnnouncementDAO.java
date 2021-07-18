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
    void deleteAnnouncement(AnnouncementImpl announcement);
    AnnouncementImpl editAnnouncement(AnnouncementImpl announcement, Map<String, String> mapAnnouncement);
    void addParticipant(AnnouncementImpl announcement, UserImpl user);
    List<AnnouncementImpl> getPopular();
    Collection<AnnouncementImpl> getLikedByUser(UserImpl user);
    void getParticipantById(AnnouncementImpl announcement, UserImpl user);
    AnnouncementImpl getByTitle(String title);
    AnnouncementImpl getPopular(BigInteger number);

}
