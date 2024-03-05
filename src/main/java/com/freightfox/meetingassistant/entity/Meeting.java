package com.freightfox.meetingassistant.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Meeting {

    @Id
    Long meetingId;
    String title;
    String description;

    @ManyToOne
    @JoinColumn(
            name = "host_id",
            referencedColumnName = "userId"
    )
    User hostId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime endTime;

    @ManyToMany
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

}
