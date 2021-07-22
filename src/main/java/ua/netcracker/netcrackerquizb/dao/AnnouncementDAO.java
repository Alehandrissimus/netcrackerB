package ua.netcracker.netcrackerquizb.dao;

import ua.netcracker.netcrackerquizb.model.Announcement;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface AnnouncementDAO {
    void deleteAnnouncement(BigInteger id_announcement);

    void createAnnouncement(Announcement newAnnouncement);

    void editAnnouncement(Announcement newAnnouncement);

    void addParticipant(BigInteger id_announcement, BigInteger id_user);

    boolean getParticipantById(BigInteger id_announcement, BigInteger id_user);

    Announcement getByTitle(String title);

    Set<Announcement> getLikedByUser(BigInteger id_user);

    List<Announcement> getPopular(int number);

}
