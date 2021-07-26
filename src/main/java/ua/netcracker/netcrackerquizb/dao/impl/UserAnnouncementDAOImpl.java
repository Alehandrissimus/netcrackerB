package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.UserAnnouncementDAO;
import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExist;
import ua.netcracker.netcrackerquizb.exception.DaoLogicException;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.UserActive;
import ua.netcracker.netcrackerquizb.model.UserRoles;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static ua.netcracker.netcrackerquizb.dao.AnnouncementDAO.*;


@Repository
public class UserAnnouncementDAOImpl implements UserAnnouncementDAO {

    private Connection connection;
    private final Properties properties = new Properties();
    private static final Logger log = Logger.getLogger(UserAnnouncementDAOImpl.class);

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    @Autowired
    public UserAnnouncementDAOImpl(
            @Value(URL_PROPERTY) String URL,
            @Value(USERNAME_PROPERTY) String USERNAME,
            @Value(PASSWORD_PROPERTY) String PASSWORD
    ) throws SQLException, ClassNotFoundException, IOException {
        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;

        getDataSource(URL, USERNAME, PASSWORD);
    }


    public void setTestConnection() throws SQLException, ClassNotFoundException, IOException {
        getDataSource(URL, USERNAME + "_TEST", PASSWORD);
    }

    private void getDataSource(String URL, String USERNAME, String PASSWORD)
            throws SQLException, ClassNotFoundException, IOException {
        try (FileInputStream fis = new FileInputStream(PATH_PROPERTY)) {
            Class.forName(DRIVER_PATH_PROPERTY);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            properties.load(fis);
        } catch (IOException e) {
            log.error("Driver error " + e.getMessage());
            throw new IOException();
        } catch (ClassNotFoundException e) {
            log.error("Property file error " + e.getMessage());
            throw new ClassNotFoundException();
        } catch (SQLException e) {
            log.error("Database connection error " + e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public Set<Announcement> getAnnouncementsLikedByUser(BigInteger idUser) throws AnnouncementDoesNotExist, DaoLogicException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(SELECT_ANNOUNCEMENT_LIKED_BY_USER));
            preparedStatement.setLong(1, idUser.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                throw new AnnouncementDoesNotExist();
            }
            Set<Announcement> announcements = new HashSet<>();
            while (resultSet.next()) {
                Announcement announcement = new AnnouncementImpl.AnnouncementBuilder()
                        .setId(BigInteger.valueOf(resultSet.getLong(ID_ANNOUNCEMENT)))
                        .setTitle(resultSet.getString(TITLE).trim())
                        .setDescription(resultSet.getString(DESCRIPTION).trim())
                        .setOwner(BigInteger.valueOf(resultSet.getLong(OWNER)))
                        .setDate(resultSet.getDate(DATE_CREATE))
                        .setAddress(resultSet.getString(ADDRESS).trim())
                        .setParticipantsCap(resultSet.getInt(LIKES))
                        .build();
                announcements.add(announcement);
            }
            return announcements;
        } catch (SQLException | AnnouncementDoesNotExist throwables) {
            log.error(throwables.getMessage(), throwables);
            return null;
        }
    }

    @Override
    public Set<User> getUsersLikedAnnouncement(BigInteger idAnnouncement) throws UserDoesNotExistException, DaoLogicException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(SELECT_USERS_LIKED_ANNOUNCEMENT));
            preparedStatement.setLong(1, idAnnouncement.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                throw new UserDoesNotExistException();
            }
            Set<User> users = new HashSet<>();
            while (resultSet.next()) {
                User user = new UserImpl.UserBuilder()
                        .setId(BigInteger.valueOf(resultSet.getLong(properties.getProperty(USER_ID))))
                        .setFirstName(resultSet.getString(properties.getProperty(USER_FIRST_NAME)))
                        .setLastName(resultSet.getString(properties.getProperty(USER_LAST_NAME)))
                        .setEmail(resultSet.getString(properties.getProperty(USER_EMAIL)))
                        .setPassword(resultSet.getString(properties.getProperty(USER_PASSWORD)))
                        .setRole(
                                UserRoles.convertFromIntToRole(resultSet.getInt(properties.getProperty(USER_ROLE))))
                        .setActive(
                                resultSet.getInt(properties.getProperty(USER_ACTIVE)) == UserActive.ACTIVE.ordinal())
                        .setEmailCode(resultSet.getString(properties.getProperty(USER_EMAIL_CODE)))
                        .setDescription(resultSet.getString(properties.getProperty(USER_DESCRIPTION)))
                        .build();
                users.add(user);
            }
            return users;
        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throw new DaoLogicException();
        }
    }

    @Override
    public boolean getParticipantById(BigInteger idAnnouncement, BigInteger idUser) throws DaoLogicException{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(GET_PARTICIPANT_BY_ID));
            preparedStatement.setLong(1, idAnnouncement.longValue());
            preparedStatement.setLong(2, idUser.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()) {
                return true;
            }
        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throw new DaoLogicException();
        }
        return false;
    }

    @Override
    public void addParticipant(BigInteger idAnnouncement, BigInteger idUser) throws DaoLogicException{
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(ADD_PARTICIPANT));
            preparedStatement.setLong(1, idAnnouncement.longValue());
            preparedStatement.setLong(2, idUser.longValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throw new DaoLogicException();
        }
    }
}
