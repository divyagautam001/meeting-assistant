package com.freightfox.meetingassistant.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long meetingId;
    String title;
    String description;

    @ManyToOne
    @JoinColumn(
            name = "host_id",
            referencedColumnName = "userId"
    )
    User host;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime endTime;

    @ManyToMany(
            cascade = CascadeType.PERSIST
    )
    @JoinTable(
            name = "user_meeting_map",
            joinColumns = @JoinColumn(
                    name = "meeting_id",
                    referencedColumnName = "meetingId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "userId"
            )
    )
    List<User> users;

    public Meeting() {
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getHostId() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setUsersFromParticipants(List<Long> participants){

    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetingId=" + meetingId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", host=" + host +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
