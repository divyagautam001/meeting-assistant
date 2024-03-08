package com.freightfox.meetingassistant.controller;

import com.freightfox.meetingassistant.entity.*;
import com.freightfox.meetingassistant.service.BookingService;
import com.freightfox.meetingassistant.service.ConflictCheckService;
import com.freightfox.meetingassistant.service.SlotCheckService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WebController {

    Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ConflictCheckService conflictCheckService;

    @Autowired
    private SlotCheckService slotCheckService;

    @PostMapping("/meetings/book")
    public ResponseEntity<Map<String, Object>> bookMeeting(@RequestBody @Valid MeetingRequest meetingRequest) {
        logger.info(meetingRequest.toString());
        Meeting createdMeeting = bookingService.bookMeeting(meetingRequest);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Meeting booked successfully");
        responseBody.put("meetingId", createdMeeting.getMeetingId());
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/meetings/free-slots")
    public ResponseEntity<Map<String, List<TimeSlot>>> getFreeSlotsBetweenTwoUsers(@RequestParam Long userOneId, @RequestParam Long userTwoId, @RequestParam Integer duration) {
        logger.info("user 1 - {} \n user 2 - {} \n duration - {}", userOneId, userTwoId, duration);
        List<TimeSlot> freeTimeSlots = slotCheckService.getFreeTimeSlots(userOneId,userTwoId,duration);
        Map<String, List<TimeSlot>> temp = new HashMap<>();
          temp.put("freeSlots", freeTimeSlots);
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/meetings/{meetingId}/check-conflicts")
    public ResponseEntity<Map<String, List<UserDto>>> getConflictingParticipants(@PathVariable Long meetingId) {
        logger.info("meeting id - {}", meetingId);
        List<UserDto> conflictingUserIdList = conflictCheckService.getConflictingParticipants(meetingId);
        Map<String, List<UserDto>> temp = new HashMap<>();
        temp.put("conflictingParticipants", conflictingUserIdList);
        return ResponseEntity.ok(temp);
    }
}
