package ua.netcracker.netcrackerquizb.util;

public interface RegexPatterns {

    String mailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    //Minimum eight characters, at least one uppercase letter, one lowercase letter and one number
    String passPattern = "\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$\"\n";

}
