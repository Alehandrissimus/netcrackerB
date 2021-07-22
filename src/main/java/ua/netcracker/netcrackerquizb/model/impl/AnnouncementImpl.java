package ua.netcracker.netcrackerquizb.model.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import ua.netcracker.netcrackerquizb.model.Announcement;

public class AnnouncementImpl implements Announcement {

  private BigInteger id;
  private String title;
  private String description;
  private BigInteger owner;
  private Date date;
  private String address;
  private Collection<BigInteger> participants;
  private int participantsCap;


  @Override
  public BigInteger getId() {
    return id;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public BigInteger getOwner() {
    return owner;
  }

  @Override
  public Date getDate() {
    return date;
  }

  @Override
  public String getAddress() {
    return address;
  }

  @Override
  public Collection<BigInteger> getParticipants() {
    return participants;
  }

  @Override
  public int getParticipantsCap() {
    return participantsCap;
  }


  public void setId(BigInteger id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setOwner(BigInteger owner) {
    this.owner = owner;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setParticipants(Collection<BigInteger> participants) {
    this.participants = participants;
  }

  public void setParticipantsCap(int participantsCap) {
    this.participantsCap = participantsCap;
  }
}
