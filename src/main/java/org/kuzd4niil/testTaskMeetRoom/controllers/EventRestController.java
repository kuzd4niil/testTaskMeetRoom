package org.kuzd4niil.testTaskMeetRoom.controllers;

import org.kuzd4niil.testTaskMeetRoom.entities.Event;
import org.kuzd4niil.testTaskMeetRoom.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-14
 */
@RestController
@RequestMapping("api/v1/events")
public class EventRestController {
    private EventService eventService;

    @Autowired
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getEvents(@RequestParam(name = "currentTime") Date currentTimeInMilliseconds) {
        List<Event> eventList = eventService.getEvents(currentTimeInMilliseconds);
        return eventList;
    }

    @PostMapping
    public Event addNewEvent(@RequestBody Event event) {
        Event newEvent = eventService.addEvent(event);
        return newEvent;
    }

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
