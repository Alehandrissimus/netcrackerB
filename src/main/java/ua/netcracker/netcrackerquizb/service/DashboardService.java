package ua.netcracker.netcrackerquizb.service;

import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Dashboard;
import ua.netcracker.netcrackerquizb.model.User;

public interface DashboardService {

    Dashboard generateDashboard(User user) throws DAOLogicException, QuizDoesNotExistException, AnnouncementDoesNotExistException, AnnouncementException;

    void setTestConnection() throws DAOConfigException;
}
