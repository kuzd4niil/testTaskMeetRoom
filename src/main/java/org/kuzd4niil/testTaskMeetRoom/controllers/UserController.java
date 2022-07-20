package org.kuzd4niil.testTaskMeetRoom.controllers;

import org.kuzd4niil.testTaskMeetRoom.entities.User;
import org.kuzd4niil.testTaskMeetRoom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-14
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Principal getUserName(Principal principal) {
        return principal;
    }

    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}