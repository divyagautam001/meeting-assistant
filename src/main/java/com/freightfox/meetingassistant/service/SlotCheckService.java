package com.freightfox.meetingassistant.service;

import com.freightfox.meetingassistant.Exception.UserNotFoundException;
import com.freightfox.meetingassistant.entity.Meeting;
import com.freightfox.meetingassistant.entity.TimeSlot;
import com.freightfox.meetingassistant.entity.User;
import com.freightfox.meetingassistant.repository.MeetingRepository;
import com.freightfox.meetingassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlotCheckService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingRepository meetingRepository;

    public List<TimeSlot> getFreeTimeSlots(Long userOneId, Long userTwoId, Integer duration){
        User userOne = userRepository.findById(userOneId).orElse(null);
        if(userOne==null){
            throw new UserNotFoundException(userOneId);
        }
        User userTwo = userRepository.findById(userTwoId).orElse(null);
        if(userTwo==null){
            throw new UserNotFoundException(userTwoId);
        }
        List<TimeSlot> availableSlots = new ArrayList<>();


        List<Meeting> meetings = meetingRepository.findMeetings(userOneId,userTwoId,LocalDateTime.now() ,LocalDateTime.now());
        List<TimeSlot> existingTimeSlots = getExistingTimeSlots(meetings);

        return getAvailableSlots(existingTimeSlots,duration);
    }

    private List<TimeSlot> getAvailableSlots(List<TimeSlot> existingTimeSlots, Integer duration) {
        List<TimeSlot> availableTimeSlots = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.now();
        for(TimeSlot existingTimeSlot: existingTimeSlots){
            long freeDuration = ChronoUnit.MINUTES.between(startTime, existingTimeSlot.getStartTime());
            while (freeDuration>=480){
                    LocalDateTime newEndTime = startTime.plusMinutes(480);
                    TimeSlot newTimeSlot = new TimeSlot(startTime,newEndTime);
                    availableTimeSlots.add(newTimeSlot);
                    if(availableTimeSlots.size()==10) return availableTimeSlots;
                    startTime = newEndTime;
                    freeDuration = ChronoUnit.MINUTES.between(startTime, existingTimeSlot.getStartTime());
            }
            if(freeDuration<480 && freeDuration>=duration){
                TimeSlot newTimeSlot = new TimeSlot(startTime,existingTimeSlot.getStartTime());
                availableTimeSlots.add(newTimeSlot);
                if(availableTimeSlots.size()==10) return availableTimeSlots;
            }
            startTime = existingTimeSlot.getEndTime();
        }
        while (availableTimeSlots.size()<10){
            LocalDateTime newEndTime = startTime.plusMinutes(480);
            TimeSlot newTimeSlot = new TimeSlot(startTime,newEndTime);
            availableTimeSlots.add(newTimeSlot);
            startTime = newEndTime;
        }
        return availableTimeSlots;
    }

    public List<TimeSlot> getExistingTimeSlots(List<Meeting> meetings){
        List<TimeSlot> existingTimeSlots = new ArrayList<>();
        TimeSlot prev = null;
        for(Meeting meeting: meetings){
            TimeSlot curr = new TimeSlot(meeting.getStartTime(),meeting.getEndTime());
            // update end time if previous time slot end is greater than start of current time slot
            if(prev!=null){
                if(prev.getEndTime().isAfter(curr.getStartTime())){
                    prev.setEndTime(curr.getEndTime());
                    continue;
                }
            }

            existingTimeSlots.add(curr);
            prev = curr;
        }
        return existingTimeSlots;
    }
}
