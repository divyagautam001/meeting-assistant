package com.freightfox.meetingassistant.service;

import com.freightfox.meetingassistant.Exception.MeetingNotFoundException;
import com.freightfox.meetingassistant.entity.Meeting;
import com.freightfox.meetingassistant.entity.User;
import com.freightfox.meetingassistant.entity.UserDto;
import com.freightfox.meetingassistant.repository.MeetingRepository;
import com.freightfox.meetingassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConflictCheckService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingRepository meetingRepository;

    public List<UserDto> getConflictingParticipants(Long meetingId) {

        Meeting given_meeting = meetingRepository.findById(meetingId).orElse(null);
        if (given_meeting == null) {
            throw new MeetingNotFoundException(meetingId);
        }
        return userRepository.getConflictingUserIdList(given_meeting.getMeetingId(), given_meeting.getStartTime(), given_meeting.getEndTime());

    }
}
