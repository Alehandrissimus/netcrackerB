package ua.netcracker.netcrackerquizb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ua.netcracker.netcrackerquizb.dao.AnnouncementDAO;
import ua.netcracker.netcrackerquizb.exception.AnnouncementDoesNotExist;
import ua.netcracker.netcrackerquizb.exception.DAOLogicException;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.impl.AnnouncementImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
public class AnnouncementDAOImpl implements AnnouncementDAO {
    private Connection connection;
    private final Properties properties = new Properties();
    private static final Logger log = Logger.getLogger(AnnouncementDAOImpl.class);

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    @Autowired
    public AnnouncementDAOImpl(
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
    public Announcement getByTitle(String title) throws AnnouncementDoesNotExist, DAOLogicException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(SELECT_ANNOUNCEMENT_BY_TITLE));
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                throw new AnnouncementDoesNotExist();
            }
            resultSet.next();
            return new AnnouncementImpl.AnnouncementBuilder()
                    .setId(BigInteger.valueOf(resultSet.getLong(ID_ANNOUNCEMENT)))
                    .setTitle(resultSet.getString(TITLE).trim())
                    .setDescription(resultSet.getString(DESCRIPTION).trim())
                    .setOwner(BigInteger.valueOf(resultSet.getLong(OWNER)))
                    .setDate(resultSet.getDate(DATE_CREATE))
                    .setAddress(resultSet.getString(ADDRESS).trim())
                    .setParticipantsCap(resultSet.getInt(LIKES))
                    .build();

        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throw new DAOLogicException();
        }
    }

    @Override
    public Set<Announcement> getSetByTitle(String title) throws AnnouncementDoesNotExist, DAOLogicException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(SELECT_SET_ANNOUNCEMENT_BY_TITLE));
            preparedStatement.setString(1, title + "%");
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
        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throw new DAOLogicException();
        }
    }

    @Override
    public BigInteger createAnnouncement(Announcement newAnnouncement) throws DAOLogicException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(CREATE_ANNOUNCEMENT), new String[]{ID_ANNOUNCEMENT});
            preparedStatement.setString(1, newAnnouncement.getTitle());
            preparedStatement.setString(2, newAnnouncement.getDescription());
            preparedStatement.setLong(3, newAnnouncement.getOwner().longValue());
            preparedStatement.setDate(4,  new Date(System.currentTimeMillis()));
            preparedStatement.setString(5, newAnnouncement.getAddress());
            preparedStatement.setInt(6, newAnnouncement.getParticipantsCap());
            int idAnnouncement = preparedStatement.executeUpdate();
            if (idAnnouncement > 0)
            {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                rs.next();
                return BigInteger.valueOf(rs.getLong(1));
            }
            return BigInteger.ZERO;

        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throw new DAOLogicException();
        }
    }

    @Override
    public void editAnnouncement(Announcement newAnnouncement) throws DAOLogicException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(UPDATE_ANNOUNCEMENT));
            preparedStatement.setString(1, newAnnouncement.getTitle());
            preparedStatement.setString(2, newAnnouncement.getDescription());
            preparedStatement.setString(3, newAnnouncement.getAddress());
            preparedStatement.setLong(4, newAnnouncement.getId().longValue());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throw new DAOLogicException();
        }
    }

    @Override
    public void deleteAnnouncement(BigInteger idAnnouncement) throws DAOLogicException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(DELETE_ANNOUNCEMENT_BY_ID));
            preparedStatement.setLong(1, idAnnouncement.longValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throw new DAOLogicException();
        }
    }

    @Override
    public List<Announcement> getPopular(int number) throws AnnouncementDoesNotExist, DAOLogicException {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(GET_POPULAR_ANNOUNCEMENT));
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                throw new AnnouncementDoesNotExist();
            }
            List<Announcement> popularAnnouncement = new ArrayList<>();
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

                popularAnnouncement.add(announcement);
            }
            return popularAnnouncement;
        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throw new DAOLogicException();
        }
    }

    @Override
    public Announcement getAnnouncementById(BigInteger idAnnouncement) throws AnnouncementDoesNotExist, DAOLogicException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(properties.getProperty(GET_ANNOUNCEMENT_BY_ID));
            preparedStatement.setLong(1, idAnnouncement.longValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                throw new AnnouncementDoesNotExist();
            }
            resultSet.next();
            return new AnnouncementImpl.AnnouncementBuilder()
                    .setId(BigInteger.valueOf(resultSet.getLong(ID_ANNOUNCEMENT)))
                    .setTitle(resultSet.getString(TITLE).trim())
                    .setDescription(resultSet.getString(DESCRIPTION).trim())
                    .setOwner(BigInteger.valueOf(resultSet.getLong(OWNER)))
                    .setDate(resultSet.getDate(DATE_CREATE))
                    .setAddress(resultSet.getString(ADDRESS).trim())
                    .setParticipantsCap(resultSet.getInt(LIKES))
                    .build();
        } catch (SQLException throwables) {
            log.error(DAO_LOGIC_EXCEPTION + throwables.getMessage());
            throwables.printStackTrace();
            throw new DAOLogicException();
        }
    }
}
