package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Announcement;
import ua.netcracker.netcrackerquizb.model.Dashboard;
import ua.netcracker.netcrackerquizb.model.Quiz;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.DashboardImpl;
import ua.netcracker.netcrackerquizb.service.AnnouncementService;
import ua.netcracker.netcrackerquizb.service.DashboardService;
import ua.netcracker.netcrackerquizb.service.QuizService;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import static ua.netcracker.netcrackerquizb.exception.MessagesForException.ANNOUNCEMENT_NOT_FOUND_EXCEPTION;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.QUIZ_NOT_FOUND_EXCEPTION;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final QuizService quizService;
    private final AnnouncementService announcementService;
    private static final Logger log = Logger.getLogger(DashboardServiceImpl.class);

    private static final int DASHBOARD_COUNT_POPULAR_ANNOUNCEMENT = 2;
    private static final BigInteger DASHBOARD_COUNT_LAST_CREATED_QUIZZES = BigInteger.valueOf(3);

    @Autowired
    public DashboardServiceImpl(QuizService quizService, AnnouncementService announcementService) {
        this.quizService = quizService;
        this.announcementService = announcementService;
    }

    @Override
    public void setTestConnection() throws DAOConfigException {
        quizService.setTestConnection();
        announcementService.setTestConnection();
    }

    @Override
    public Dashboard generateDashboard(User user) throws DAOLogicException,
            QuizDoesNotExistException, AnnouncementDoesNotExistException, AnnouncementException {

        List<Quiz> lastQuizzes = quizService.getLastCreatedQuizzes(DASHBOARD_COUNT_LAST_CREATED_QUIZZES);

        if(lastQuizzes.isEmpty()) {
            log.error(QUIZ_NOT_FOUND_EXCEPTION + lastQuizzes);
            throw new QuizDoesNotExistException(QUIZ_NOT_FOUND_EXCEPTION);
        }

        List<Announcement> popularAnnouncement =
                announcementService.getPopularAnnouncements(DASHBOARD_COUNT_POPULAR_ANNOUNCEMENT);

        if(popularAnnouncement.isEmpty()) {
            log.error(ANNOUNCEMENT_NOT_FOUND_EXCEPTION + popularAnnouncement);
            throw new AnnouncementDoesNotExistException(ANNOUNCEMENT_NOT_FOUND_EXCEPTION);
        }

        Set<Announcement> likedAnnouncement = announcementService.getAnnouncementsLikedByUser(user.getId());

        return new DashboardImpl(lastQuizzes, popularAnnouncement, likedAnnouncement);
    }

}
