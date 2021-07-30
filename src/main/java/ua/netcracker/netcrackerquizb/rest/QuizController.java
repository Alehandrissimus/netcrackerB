package ua.netcracker.netcrackerquizb.rest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.netcrackerquizb.exception.*;
import ua.netcracker.netcrackerquizb.model.*;
import ua.netcracker.netcrackerquizb.service.QuizService;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import static ua.netcracker.netcrackerquizb.exception.MessagesForException.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    private static final Logger log = Logger.getLogger(QuizController.class);

    @GetMapping("/")
    public List<Quiz> showAllQuizzes() throws DAOLogicException, QuizDoesNotExistException {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        if(quizzes.isEmpty()) {
            log.error(QUIZ_NOT_FOUND_EXCEPTION);
            throw new QuizDoesNotExistException(QUIZ_NOT_FOUND_EXCEPTION);
        }
        return quizzes;
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable BigInteger id) throws DAOLogicException, QuizDoesNotExistException, QuizException {
        Quiz quiz = quizService.getQuizById(id);
        if(quiz == null) {
            log.error(QUIZ_NOT_FOUND_EXCEPTION);
            throw new QuizDoesNotExistException(QUIZ_NOT_FOUND_EXCEPTION);
        }
        return quiz;
    }

    @PostMapping("/create")
    public Quiz createQuiz(String title, String description, QuizType quizType,
                           List<Question> questions, BigInteger creatorId) throws DAOLogicException, UserException, QuizException {
        if(StringUtils.isBlank(title)) {
            throw new QuizException(EMPTY_TITLE);
        }
        if(StringUtils.isBlank(description)) {
            throw new QuizException(EMPTY_DESCRIPTION);
        }
        if(creatorId == null) {
            throw new UserException(USER_NOT_FOUND_EXCEPTION);
        }
        return quizService.buildNewQuiz(title, description, quizType, questions, creatorId);
    }

    @PutMapping("/{id}")
    public void updateQuiz(@PathVariable BigInteger id) throws DAOLogicException, QuizDoesNotExistException, QuizException {
        Quiz updatedQuiz = quizService.getQuizById(id);
        if(updatedQuiz == null) {
            log.error(QUIZ_NOT_FOUND_EXCEPTION);
            throw new QuizDoesNotExistException(QUIZ_NOT_FOUND_EXCEPTION);
        }
       quizService.updateQuiz(updatedQuiz);
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable BigInteger id) throws QuizDoesNotExistException, DAOLogicException, QuizException {
        Quiz quiz = quizService.getQuizById(id);
        if(quiz == null) {
            log.error(QUIZ_NOT_FOUND_EXCEPTION);
            throw new QuizDoesNotExistException(QUIZ_NOT_FOUND_EXCEPTION);
        }
        try {
            quizService.deleteQuiz(quiz);
        } catch (DAOLogicException | UserDoesNotExistException | UserException e) {
            throw new DAOLogicException(DAO_LOGIC_EXCEPTION + e.getMessage());
        }
    }


    @GetMapping("/filter")
    public List<Quiz> showAllFilterQuizzes(User user, Filter filter) {
        switch(filter) {
            case DATE:
                break;
            case QUIZTYPE:
                break;
            case COMPLETED:
                break;
            case FAVORITES:
                break;
            default:
        }
        return null;
    }

    @GetMapping("/finish")
    public void finishQuiz(String title, User user, Collection<Answer> answers) {

    }


}
