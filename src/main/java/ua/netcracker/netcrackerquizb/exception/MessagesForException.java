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

}
