package com.freightfox.meetingassistant.repository;

import com.freightfox.meetingassistant.entity.Meeting;
import com.freightfox.meetingassistant.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {

    @Query("""
            SELECT m FROM Meeting m
            WHERE m.id IN (
                SELECT Distinct(m1.meetingId)
                FROM Meeting m1
                JOIN m1.users u
                Where (u.userId = ?1 OR u.userId = ?2)
            )
            AND (m.startTime> ?3 OR m.endTime> ?4)
            ORDER BY m.startTime, m.endTime ASC
            """)
    List<Meeting> findMeetings(long userOneId, long userTwoId, LocalDateTime currTimeStart, LocalDateTime currTimeEnd);
}
