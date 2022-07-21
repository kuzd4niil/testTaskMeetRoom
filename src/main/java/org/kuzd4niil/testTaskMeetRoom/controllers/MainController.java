package org.kuzd4niil.testTaskMeetRoom.controllers;

import org.kuzd4niil.testTaskMeetRoom.entities.Event;
import org.kuzd4niil.testTaskMeetRoom.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-14
 */
@Controller
@RequestMapping
public class MainController {
    private EventService eventService;
    @Autowired
    public MainController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String index() {
        return "meetRoom";
    }
}