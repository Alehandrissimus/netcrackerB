package ua.netcracker.netcrackerquizb.exception;

public class AnnouncementDoesNotExist extends Exception{
    @Override
    public String toString() {
        return "Announcement does not exist!\n" + super.toString();
    }
}