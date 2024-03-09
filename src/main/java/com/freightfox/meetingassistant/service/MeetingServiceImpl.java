package com.freightfox.meetingassistant.service;

import com.freightfox.meetingassistant.Exception.UserNotFoundException;
import com.freightfox.meetingassistant.entity.Meeting;
import com.freightfox.meetingassistant.entity.MeetingRequest;
import com.freightfox.meetingassistant.entity.User;
import com.freightfox.meetingassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private UserRepository userRepository; // Assuming you have a repository for User entity

    public Meeting mapMeetingRequestToEntity(MeetingRequest meetingRequest) {
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingRequest.getTitle());
        meeting.setDescription(meetingRequest.getDescription());
        meeting.setStartTime(meetingRequest.getStartTime());
        meeting.setEndTime(meetingRequest.getEndTime());
        User host = userRepository.findById(meetingRequest.getHostId()).orElse(null);
        if (host == null) {
            throw new UserNotFoundException(meetingRequest.getHostId());
        }
        meeting.setHost(host);

        Set<User> participants = new HashSet<>();
        for (Long participantId : meetingRequest.getParticipants()) {
            User participant = userRepository.findById(participantId).orElse(null);
            if (participant != null) {
                participants.add(participant);
            } else {
                throw new UserNotFoundException(participantId);
            }
        }
        participants.add(host);
        meeting.setUsers(participants);

        return meeting;
    }
}
