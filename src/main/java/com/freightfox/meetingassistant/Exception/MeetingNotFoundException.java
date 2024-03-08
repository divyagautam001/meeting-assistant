package com.freightfox.meetingassistant.Exception;

public class MeetingNotFoundException extends RuntimeException{

    public MeetingNotFoundException(Long id) {
        super("Meeting not found with id : "+id);
    }
}
