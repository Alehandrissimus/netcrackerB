package ua.netcracker.netcrackerquizb.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface Announcement {
  BigInteger getId();
  String getTitle();
  String getDescription();
  BigInteger getOwner();
  Date getDate();
  String getAddress();
  Collection<BigInteger> getParticipants();
  int getParticipantsCap();

}
