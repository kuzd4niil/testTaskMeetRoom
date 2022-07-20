package org.kuzd4niil.testTaskMeetRoom.services;

import org.kuzd4niil.testTaskMeetRoom.entities.Event;
import org.kuzd4niil.testTaskMeetRoom.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-14
 */
@Service
public class EventService {
    private final static long MILLISECONDS_IN_ONE_DAY = 86400000;
    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventRepository getEventRepository() {
        return eventRepository;
    }

    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents(Date currentDate) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);

        // Convert from week which begin at Sunday to week which begin at Monday
        long numberDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        numberDayOfWeek = (numberDayOfWeek == 0) ?  (numberDayOfWeek = 6) : (numberDayOfWeek - 1);

        long timeZoneOffset = calendar.get(Calendar.ZONE_OFFSET);

        // Get beginning of week: subtract from current time (ms) a numberOfDay (ms), time offset from start of day (ms) and time zone offset (ms)
        long startOfWeekInMilliseconds = currentDate.getTime() -
                (numberDayOfWeek * MILLISECONDS_IN_ONE_DAY + currentDate.getTime() % MILLISECONDS_IN_ONE_DAY + timeZoneOffset);

        // Get ending of week
        long endOfWeekInMilliseconds = startOfWeekInMilliseconds + 7 * MILLISECONDS_IN_ONE_DAY;


        Date startOfWeek = new Date(startOfWeekInMilliseconds);
        Date endOfWeek = new Date(endOfWeekInMilliseconds);

        List<Event> events = eventRepository.getEvents(startOfWeek, endOfWeek);

        return events;
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).get();
    }
}
