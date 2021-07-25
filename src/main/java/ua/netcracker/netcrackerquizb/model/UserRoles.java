package ua.netcracker.netcrackerquizb.model;

public enum UserRoles {
  ADMIN,
  USER,
  UNVERIFIED;

  public static UserRoles convertFromIntToRole(int number) {
    switch (number) {
      case 0:
        return ADMIN;
      case 1:
        return USER;
      default:
        return UNVERIFIED;
    }
  }

}
