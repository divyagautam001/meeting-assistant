package com.freightfox.meetingassistant.controller;

import com.freightfox.meetingassistant.entity.MeetingRequest;
import com.freightfox.meetingassistant.entity.TimeSlot;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class WebController {

    Logger logger = LoggerFactory.getLogger(WebController.class);

    @PostMapping("/meetings/book")
    public ResponseEntity<Map<String, String>> bookMeeting(@RequestBody @Valid MeetingRequest meetingRequest) {
        logger.info(meetingRequest.toString());
        Map<String, String> temp = new HashMap<>();
        temp.put("message", "Meeting booked successfully");
        temp.put("meetingId", "122334");
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/meetings/free-slots")
    public ResponseEntity<Map<String,List<TimeSlot>>> getFreeSlotsBetweenTwoUsers(@RequestParam Long userOneId, @RequestParam Long userTwoId, @RequestParam Integer duration) {
        logger.info("user 1 - {} \n user 2 - {} \n duration - {}", userOneId, userTwoId, duration);
        Map<String, List<TimeSlot>> temp = new HashMap<>();
        List<TimeSlot> res = new ArrayList<>();
        res.add(new TimeSlot(LocalDateTime.parse("2017-01-13T07:09:42"), LocalDateTime.parse("2017-01-13T07:09:42")));
        temp.put("freeSlots",res);
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/meetings/{meetingId}/check-conflicts")
    public ResponseEntity<Map<String, List<Long>>> getConflictingParticipants(@PathVariable Long meetingId) {
        logger.info("meeting id - {}", meetingId);
        Map<String, List<Long>> temp = new HashMap<>();
        List<Long> res = new ArrayList<>();
        res.add(12345L);
        temp.put("conflictingParticipants", res);
        return ResponseEntity.ok(temp);
    }
}
