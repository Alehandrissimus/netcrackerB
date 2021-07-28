package ua.netcracker.netcrackerquizb.exception;

public interface MessagesForException {

    String DAO_LOGIC_EXCEPTION = "Dao logic exception ";
    String ANNOUNCEMENT_NOT_FOUND_EXCEPTION = "Announcement does not exist!";
    String ANNOUNCEMENT_HAS_NOT_BEEN_RECEIVED = "Announcement has not been received";
    String ANNOUNCEMENT_ALREADY_EXISTS = "Announcement with the same name already exists";
    String EMPTY_ANNOUNCEMENT_TITLE = "Title field cannot be empty";
    String TITLE_TOO_LONG = "Length of the title field cannot exceed 50 characters";
    String EMPTY_ANNOUNCEMENT_DESCRIPTION = "Description field cannot be empty";
    String DESCRIPTION_TOO_LONG = "Length of the description field cannot exceed 300 characters";
    String EMPTY_ANNOUNCEMENT_ADDRESS = "Address field cannot be empty";
    String ADDRESS_TOO_LONG = "Length of the address field cannot exceed 30 characters";
    String OWNER_IS_NULL = "Owner field cannot be empty";

    String testConnectionError = "Error while setting test connection %s";
    String getQuestionByIdNotFoundErr = "QuestionDoesNotExistException in getQuestionById, questionId = %d";
    String getQuestionByIdNotFoundExc = "getQuestionById() not found question by id = %s";
    String getQuestionByDataNotFoundErr = "QuestionDoesNotExistException in getQuestionByData, questionText = %s, quiz = %d";
    String getQuestionByDataNotFoundExc = "getQuestionByData not found question by questionText = %s, quiz = %d";
    String getQuestionByDataLogicErr = "SQL Exception while getQuestionByData in QuestionDAOImpl";
    String getQuestionByDataLogicExc = "SQLException in getQuestionByData";
    String GetQuestionByIdLogicErr = "SQL Exception while getQuestionById in QuestionDAOImpl";
    String GetQuestionByIdLogicExc = "SQLException in getQuestionById";
    String createQuestionNotFoundErr = "QuestionDoesNotExistException in createQuestion, questionText = %s, quizId = %d";
    String createQuestionNotFoundExc = "createQuestion not found in new created question, questionText = %s, quizId = %d";
    String createQuestionLogicErr = "SQL Exception while createQuestion in QuestionDAOImpl";
    String createQuestionLogicExc = "SQLException in createQuestion";
    String deleteQuestionLogicErr = "SQL Exception while deleteQuestion in QuestionDAOImpl";
    String deleteQuestionLogicExc = "SQLException in deleteQuestion";
    String getAllQuestionsNotFoundErr = "QuestionDoesNotExistException in getAllQuestions , quizId = %s";
    String getAllQuestionsNotFoundExc = "getAllQuestions have not found any questions by quizId = %s";
    String getAllQuestionsLogicErr = "SQL Exception while getAllQuestions in QuestionDAOImpl";
    String getAllQuestionsLogicExc = "SQLException in getAllQuestions";
    String updateQuestionLogicErr = "SQL Exception while updateQuestion in QuestionDAOImpl";
    String updateQuestionLogicExc = "SQLException in updateQuestion";

}
