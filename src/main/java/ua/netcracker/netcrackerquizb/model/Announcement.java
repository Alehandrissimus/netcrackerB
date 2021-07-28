package ua.netcracker.netcrackerquizb.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public interface Announcement {

  int MAX_LENGTH_TITLE = 50;
  int MAX_LENGTH_DESCRIPTION = 300;
  int MAX_LENGTH_ADDRESS = 30;

  BigInteger getId();
  String getTitle();
  String getDescription();
  BigInteger getOwner();
  Date getDate();
  String getAddress();
  Collection<BigInteger> getParticipants();
  int getParticipantsCap();

  void setId(BigInteger id);
  void setTitle(String title);
  void setDescription(String description);
  void setOwner(BigInteger owner);
  void setDate(Date date);
  void setAddress(String address);
  void setParticipants(Collection<BigInteger> participants);
  void setParticipantsCap(int participantsCap);

}
