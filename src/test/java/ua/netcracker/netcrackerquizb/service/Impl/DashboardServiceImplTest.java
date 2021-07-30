package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.Dashboard;
import ua.netcracker.netcrackerquizb.model.User;
import ua.netcracker.netcrackerquizb.model.impl.UserImpl;
import ua.netcracker.netcrackerquizb.service.DashboardService;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ua.netcracker.netcrackerquizb.exception.MessagesForException.ERROR_WHILE_SETTING_TEST_CONNECTION;


@SpringBootTest
public class DashboardServiceImplTest {

    @Autowired
    private DashboardService dashboardService;

    private static final Logger log = Logger.getLogger(DashboardServiceImplTest.class);

    @Autowired
    private void setTestConnection(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
        try {
            dashboardService.setTestConnection();
        } catch (DAOConfigException e) {
            log.error(ERROR_WHILE_SETTING_TEST_CONNECTION + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void generateDashboard() {
        User user = new UserImpl.UserBuilder()
                .setId(BigInteger.valueOf(1))
                .build();

        try {
            Dashboard dashboard = dashboardService.generateDashboard(user);
            assertNotNull(dashboard);
        } catch (DAOLogicException | AnnouncementException | AnnouncementDoesNotExistException | QuizDoesNotExistException e) {
            e.printStackTrace();
        }
    }
}
