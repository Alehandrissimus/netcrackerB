package ua.netcracker.netcrackerquizb.exception;

public interface MessagesForException {
    String ERROR_WHILE_SETTING_TEST_CONNECTION = "Error while setting test connection ";
    String DAO_LOGIC_EXCEPTION = "Dao logic exception ";
    String EMPTY_ID = "Id cannot be empty";
    String EMPTY_TITLE = "Title field cannot be empty";
    String EMPTY_DESCRIPTION = "Description field cannot be empty";
    String TITLE_TOO_LONG = "Length of the title field cannot exceed 50 characters";
    String DESCRIPTION_TOO_LONG = "Length of the description field cannot exceed 300 characters";
    String OWNER_IS_NULL = "Owner field cannot be empty";

    String ANNOUNCEMENT_NOT_FOUND_EXCEPTION = "Announcement does not exist!";
    String ANNOUNCEMENT_HAS_NOT_BEEN_RECEIVED = "Announcement has not been received";
    String EMPTY_ANNOUNCEMENT_ID = "IdAnnouncement field cannot be empty";
    String ANNOUNCEMENT_ALREADY_EXISTS = "Announcement with the same name already exists";
    String EMPTY_ANNOUNCEMENT_TITLE = "Title field cannot be empty";
    String EMPTY_ANNOUNCEMENT_DESCRIPTION = "Description field cannot be empty";
    String EMPTY_ANNOUNCEMENT_ADDRESS = "Address field cannot be empty";
    String ADDRESS_TOO_LONG = "Length of the address field cannot exceed 30 characters";

    String QUIZ_ALREADY_EXISTS = "Quiz with the same description already exists";
    String QUIZ_NOT_FOUND_EXCEPTION = "Quiz does not exist!";
    String QUIZ_HAS_NOT_BEEN_RECEIVED = "Quiz has not been received";
    String CREATE_QUIZ_EXCEPTION = "SQL Exception while createQuiz in QuizDAOImpl";
    String DELETE_QUIZ_EXCEPTION = "SQL Exception while deleteQuiz in QuizDAOImpl";
    String GET_QUIZ_BY_ID_EXCEPTION = "SQL Exception while getQuizById in QuizDAOImpl";
    String GET_ALL_QUIZZES_EXCEPTION = "SQL Exception while getAllQuizzes in QuizDAOImpl";
    String GET_LAST_FIVE_CREATED_QUIZZES_EXCEPTION = "SQL Exception while getLastFiveCreatedQuizzes in QuizDAOImpl";
    String GET_QUIZZES_BY_TITLE_EXCEPTION = "SQL Exception while getQuizzesByTitle in QuizDAOImpl";
    String GET_QUIZZES_BY_TYPE_EXCEPTION = "SQL Exception while getQuizzesByType in QuizDAOImpl";

    String TEST_CONNECTION_ERR = "Error while setting test connection %s";
    String TEST_CONNECTION_EXC = "Error while setting test connection";
    String QUESTION_NOT_FOUND = "QuestionDoesNotExistException ";
    String QUESTION_DUPLICATE = "Question title duplicate within one quiz";
    String QUESTION_EMPTY = "Question title is empty";

    String ANSWER_EMPTY = "Answer title is empty";

    String getAnswerByIdNotFoundErr = "AnswerDoesNotExistException in getAnswerById, answerId = %d";
    String getAnswerByIdNotFoundExc = "getAnswerById() not found answer by answerId = %d";
    String getAnswerByIdLogicErr = "SQL Exception while getAnswerById in AnswerDAOImpl";
    String getAnswerByIdLogicExc = "SQL Exception while getAnswerById with answerId = %d";
    String getLastAnswerIdByTitleNotFoundErr = "AnswerDoesNotExistException in getLastAnswerIdByTitle, title = %s";
    String getLastAnswerIdByTitleNotFoundExc = "getLastAnswerIdByTitle() does not found answer with title = %s";
    String getLastAnswerIdByTitleLogicErr = "SQL Exception while getLastAnswerIdByTitle in AnswerDAOImpl";
    String getLastAnswerIdByTitleLogicExc = "SQL Exception while getLastAnswerIdByTitle with title = %s";
    String createAnswerLogicExc = "SQL Exception while createAnswer in AnswerDAOImpl";
    String deleteAnswerLogicExc = "SQL Exception while deleteAnswer in AnswerDAOImpl";
    String updateAnswerLogicExc = "SQL Exception while updateAnswer in AnswerDAOImpl";
    String getAnswersByQuestionIdLogicErr = "SQL Exception while getAnswersByQuestionId in AnswerDAOImpl";
    String getAnswersByQuestionIdLogicExc = "SQL Exception while getAnswersByQuestionId with questionId = %d";

    String DONT_ENOUGH_RIGHTS = "You don't have enough rights";
    String ANNOUNCEMENT_ALREADY_LIKED = "The user has already liked this announcement";
    String ANNOUNCEMENT_HAS_NOT_LIKED = "User has not liked this announcement yet";
}
