package com.freightfox.meetingassistant.service;

import com.freightfox.meetingassistant.entity.Meeting;
import com.freightfox.meetingassistant.entity.MeetingRequest;

public interface MeetingService {
    Meeting mapMeetingRequestToEntity(MeetingRequest meetingRequest);
}
