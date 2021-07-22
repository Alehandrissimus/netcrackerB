package ua.netcracker.netcrackerquizb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.AnnouncementDAO;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import java.math.BigInteger;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
public class AnnouncementDAOImpl implements AnnouncementDAO {



    private static Connection connection;

    @Autowired
    public AnnouncementDAOImpl(
            @Value("${spring.datasource.url}") String URL,
            @Value("${spring.datasource.username}") String USERNAME,
            @Value("${spring.datasource.password}") String PASSWORD
    ) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /////////////////////////////////////////////SQL Queries//////////////////////////////////////////////////////////

    private static final String selectAnnouncementsLikedByUser = "SELECT announcement.id_announcement, announcement.title," +
            " announcement.description, announcement.ownr, " +
            "announcement.date_create, announcement.address, announcement.likes " +
            "FROM announcement_participant LEFT JOIN announcement ON " +
            "announcement_participant.id_announcement = announcement.id_announcement WHERE id_usr=?";

    private static final String deleteAnnouncementById = "DELETE FROM announcement WHERE id_announcement=?";

    private static final String addParticipant = "INSERT INTO announcement_participant VALUES(?, ?)";

    private static final String getPopularAnnouncement = "SELECT * FROM ANNOUNCEMENT ORDER BY likes desc FETCH FIRST ? ROWS ONLY";

    private static final String updateAnnouncement = "UPDATE ANNOUNCEMENT SET title=?, DESCRIPTION=?, ADDRESS=? WHERE ID_ANNOUNCEMENT=?";

    private static final String createNewAnnouncement = "INSERT INTO announcement VALUES(s_announcement.NEXTVAL, ?, ?, ?, ?, ?, ?)";

    private static final String selectAnnouncementByTittle = "SELECT * FROM ANNOUNCEMENT WHERE title=?";

    private static final String getParticipantById = "SELECT * FROM announcement_participant WHERE ID_ANNOUNCEMENT=? AND ID_USR=?";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public AnnouncementImpl getByTitle(String title) {

        AnnouncementImpl announcement = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectAnnouncementByTittle);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {

                announcement = new AnnouncementImpl();
                announcement.setId(BigInteger.valueOf(resultSet.getInt("id_announcement")));
                announcement.setTitle(resultSet.getString("title").trim());
                announcement.setDescription(resultSet.getString("description").trim());
                announcement.setOwner(BigInteger.valueOf(resultSet.getInt("ownr")));
                announcement.setDate(resultSet.getDate("date_create"));
                announcement.setAddress(resultSet.getString("address").trim());
                announcement.setParticipantsCap(resultSet.getInt("likes"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return announcement;
    }

    @Override
    public void createAnnouncement(Announcement newAnnouncement) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(createNewAnnouncement);
            preparedStatement.setString(1, newAnnouncement.getTitle());
            preparedStatement.setString(2, newAnnouncement.getDescription());
            preparedStatement.setInt(3, newAnnouncement.getOwner().intValue());
            preparedStatement.setDate(4,  new Date(new java.util.Date().getTime())); // need to discus this
            preparedStatement.setString(5, newAnnouncement.getAddress());
            preparedStatement.setInt(6, newAnnouncement.getParticipantsCap());
            preparedStatement.executeUpdate();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void editAnnouncement(Announcement newAnnouncement) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateAnnouncement);
            preparedStatement.setString(1, newAnnouncement.getTitle());
            preparedStatement.setString(2, newAnnouncement.getDescription());
            preparedStatement.setString(3, newAnnouncement.getAddress());
            preparedStatement.setInt(4, newAnnouncement.getId().intValue());
            preparedStatement.executeUpdate();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void addParticipant(BigInteger id_announcement, BigInteger id_user) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(addParticipant);
            preparedStatement.setInt(1, id_announcement.intValue());
            preparedStatement.setInt(2, id_user.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void deleteAnnouncement(BigInteger id_announcement) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAnnouncementById);
            preparedStatement.setInt(1, id_announcement.intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public List<Announcement> getPopular(int number) {
        List<Announcement> popularAnnouncement = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getPopularAnnouncement);
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AnnouncementImpl announcement = new AnnouncementImpl();
                announcement.setId(BigInteger.valueOf(resultSet.getInt("id_announcement")));
                announcement.setTitle(resultSet.getString("title").trim());
                announcement.setDescription(resultSet.getString("description").trim());
                announcement.setOwner(BigInteger.valueOf(resultSet.getInt("ownr")));
                announcement.setDate(resultSet.getDate("date_create"));
                announcement.setAddress(resultSet.getString("address").trim());
                announcement.setParticipantsCap(resultSet.getInt("likes"));

                popularAnnouncement.add(announcement);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return popularAnnouncement;
    }

    @Override
    public Set<Announcement> getLikedByUser(BigInteger id_user) {

        Set<Announcement> announcements = new HashSet<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectAnnouncementsLikedByUser);
            preparedStatement.setInt(1, id_user.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AnnouncementImpl announcement = new AnnouncementImpl();
                announcement.setId(BigInteger.valueOf(resultSet.getInt("id_announcement")));
                announcement.setTitle(resultSet.getString("title").trim());
                announcement.setDescription(resultSet.getString("description").trim());
                announcement.setOwner(BigInteger.valueOf(resultSet.getInt("ownr")));
                announcement.setDate(resultSet.getDate("date_create"));
                announcement.setAddress(resultSet.getString("address").trim());
                announcement.setParticipantsCap(resultSet.getInt("likes"));

                announcements.add(announcement);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return announcements;
    }

    /**
     * Returns true if there is a record in the database, false otherwise.
     */
    @Override
    public boolean getParticipantById(BigInteger id_announcement, BigInteger id_user) {

        boolean isRecord = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getParticipantById);
            preparedStatement.setInt(1, id_announcement.intValue());
            preparedStatement.setInt(2, id_user.intValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.isBeforeFirst()) {
                isRecord = true; // if resultSet has an entry
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return isRecord;
    }
}
