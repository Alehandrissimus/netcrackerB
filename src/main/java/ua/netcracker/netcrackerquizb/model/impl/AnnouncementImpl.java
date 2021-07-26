package ua.netcracker.netcrackerquizb.model.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import ua.netcracker.netcrackerquizb.model.Announcement;

public class AnnouncementImpl implements Announcement {

  private AnnouncementImpl() {
  }

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

  @Override
  public void setId(BigInteger id) {
    this.id = id;
  }
  @Override
  public void setTitle(String title) {
    this.title = title;
  }
  @Override
  public void setDescription(String description) {
    this.description = description;
  }
  @Override
  public void setOwner(BigInteger owner) {
    this.owner = owner;
  }
  @Override
  public void setDate(Date date) {
    this.date = date;
  }
  @Override
  public void setAddress(String address) {
    this.address = address;
  }
  @Override
  public void setParticipants(Collection<BigInteger> participants) {
    this.participants = participants;
  }
  @Override
  public void setParticipantsCap(int participantsCap) {
    this.participantsCap = participantsCap;
  }

  public static class AnnouncementBuilder {

    private final AnnouncementImpl newAnnouncement;
    public AnnouncementBuilder(){
      newAnnouncement = new AnnouncementImpl();
    }
    public AnnouncementBuilder setId(BigInteger id){
      newAnnouncement.id = id;
      return this;
    }
    public AnnouncementBuilder setTitle(String title){
      newAnnouncement.title = title;
      return this;
    }
    public AnnouncementBuilder setDescription(String description){
      newAnnouncement.description = description;
      return this;
    }
    public AnnouncementBuilder setOwner(BigInteger owner) {
      newAnnouncement.owner = owner;
      return this;
    }
    public AnnouncementBuilder setDate(Date date) {
      newAnnouncement.date = date;
      return this;
    }
    public AnnouncementBuilder setAddress(String address){
      newAnnouncement.address = address;
      return this;
    }
    public AnnouncementBuilder setParticipants(Collection<BigInteger> participants){
      newAnnouncement.participants = participants;
      return this;
    }
    public AnnouncementBuilder setParticipantsCap(int participantsCap){
      newAnnouncement.participantsCap = participantsCap;
      return this;
    }
    public Announcement build(){
      return newAnnouncement;
    }
  }
}
