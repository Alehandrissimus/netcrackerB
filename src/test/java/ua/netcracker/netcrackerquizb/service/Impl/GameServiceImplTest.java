package ua.netcracker.netcrackerquizb.service.Impl;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.netcracker.netcrackerquizb.exception.DAOConfigException;
import ua.netcracker.netcrackerquizb.service.GameService;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class GameServiceImplTest {
    private static final Logger log = Logger.getLogger(GameServiceImplTest.class);

    private GameService gameService;

    @Autowired
    private void setTestConnection(GameService gameService) {
        this.gameService = gameService;
        try {
            gameService.setTestConnection();
        } catch (DAOConfigException e) {
            log.error("Error while setting test connection " + e.getMessage());
        }
    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void sendGameQuizTest() {

    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void validateAnswersTest() {

    }

    @Test
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void setIsFavoriteTest() {

    }

}
