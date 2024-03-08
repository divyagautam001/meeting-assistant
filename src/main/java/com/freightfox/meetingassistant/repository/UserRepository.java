package com.freightfox.meetingassistant.repository;

import com.freightfox.meetingassistant.entity.User;
import com.freightfox.meetingassistant.entity.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
            SELECT new com.freightfox.meetingassistant.entity.UserDto(u.userId,u.name,u.address)
            FROM User u
            JOIN u.meetings m
            where m.meetingId <> :meetingId
            AND u.userId IN (
                SELECT DISTINCT u1.userId
                FROM User u1
                JOIN u1.meetings m1
                WHERE m1.meetingId = :meetingId
            )
            AND (
                (m.startTime BETWEEN :startTime AND :endTime)
                OR (m.endTime BETWEEN :startTime AND :endTime)
                OR (:startTime BETWEEN m.startTime AND m.endTime)
                OR (:endTime BETWEEN m.startTime AND m.endTime)
            )
            """)
    List<UserDto> getConflictingUserIdList(@Param("meetingId") Long meetingId, @Param("startTime")LocalDateTime startTime, @Param("endTime")LocalDateTime endTime);
}
