package ua.netcracker.netcrackerquizb.exception;

public class AnnouncementDoesNotExist extends Exception{

    public AnnouncementDoesNotExist(String errorMessage, Throwable error){
        super(errorMessage, error);
    }

    public AnnouncementDoesNotExist(String errorMessage){
        super(errorMessage);
    }
}