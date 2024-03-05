package com.freightfox.meetingassistant.service;

import com.freightfox.meetingassistant.Exception.UserNotFoundException;
import com.freightfox.meetingassistant.entity.Meeting;
import com.freightfox.meetingassistant.entity.MeetingRequest;
import com.freightfox.meetingassistant.repository.MeetingRepository;
import com.freightfox.meetingassistant.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingService meetingService;

    public Meeting bookMeeting(MeetingRequest meetingRequest){
        Meeting meeting = meetingService.mapMeetingRequestToEntity(meetingRequest);
        return meetingRepository.save(meeting);
    }

}
