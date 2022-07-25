package org.kuzd4niil.testTaskMeetRoom.services;

import org.kuzd4niil.testTaskMeetRoom.entities.Event;
import org.kuzd4niil.testTaskMeetRoom.exceptions.AnEventOverlapsWithAnotherEventException;
import org.kuzd4niil.testTaskMeetRoom.exceptions.InvalidStartAndEndDatesOfEventException;
import org.kuzd4niil.testTaskMeetRoom.exceptions.TimeIsNotAMultipleOfHalfOfHourException;
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
    private final static long MILLISECONDS_IN_HALF_OF_HOUR = 1800000;
    private final static Calendar calendar = Calendar.getInstance();
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
        if (event.getStartDateOfEvent().getTime() == event.getEndDateOfEvent().getTime()) {
            throw new InvalidStartAndEndDatesOfEventException("Start time and end time of event is the same exception");
        }

        if (event.getStartDateOfEvent().getTime() > event.getEndDateOfEvent().getTime()) {
            throw new InvalidStartAndEndDatesOfEventException("Start time of event can't be greater then end time");
        }

        if (event.getStartDateOfEvent().getTime() < (new Date()).getTime()) {
            throw new InvalidStartAndEndDatesOfEventException("Event can't start in the past");
        }

        if ((event.getStartDateOfEvent().getTime() - event.getEndDateOfEvent().getTime()) > MILLISECONDS_IN_ONE_DAY) {
            throw new InvalidStartAndEndDatesOfEventException("The duration of the event is more than 24 hours");
        }

        calendar.setTime(event.getStartDateOfEvent());
        long startEventDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.setTime(event.getEndDateOfEvent());
        long endEventDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if ((startEventDayOfWeek == 1) || (endEventDayOfWeek == 1)) {
            throw new InvalidStartAndEndDatesOfEventException("Meetings cannot take place in the Sun");
        }

        if ((event.getStartDateOfEvent().getTime() % MILLISECONDS_IN_HALF_OF_HOUR > 0) ||
                (event.getEndDateOfEvent().getTime() % MILLISECONDS_IN_HALF_OF_HOUR > 0)) {
            throw new TimeIsNotAMultipleOfHalfOfHourException("Time of start/end event isn't a multiple of half of hour");
        }

        List<Event> overlappingEvents = eventRepository.getOverlappingEvents(event.getStartDateOfEvent(), event.getEndDateOfEvent());

        if (overlappingEvents.size() > 0) {
            throw new AnEventOverlapsWithAnotherEventException("An event overlaps with another event exception");
        }

        return eventRepository.save(event);
    }

    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).get();
    }

    public boolean deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();

        eventRepository.delete(event);
        return true;
    }
}
