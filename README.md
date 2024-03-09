# Meeting Calendar Assistant

A calendar assistant is a program that assists the calendar owner and helps
other users book meeting with him and help them finding meeting conflicts.
----
### Features -
1. Book a new meeting
2. Given the calendar of 2 employees as input, find out the free slots(max 10) where a
   meeting of a fixed duration can be scheduled (max duration 480 mins)
3. Given a meeting request, find out all participants have meeting conflicts
---
### API Endpoints - 
```text
POST api/meetings/book
GET api/meetings/free-slots?userOneId=<userId>&userTwoId=<userId>&duration=<minutes>
GET api/meetings/<meetingId>/check-conflicts
```

