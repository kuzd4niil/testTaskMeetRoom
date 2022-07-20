package org.kuzd4niil.testTaskMeetRoom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-13
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @Column(
            name = "id"
    )
    private Long id;
    @Column(
            name = "username",
            unique = true
    )
    private String username;
    @JsonIgnore
    @Column(
            name = "password",
            nullable = false
    )
    private String password;
    @JsonIgnore
    @ManyToMany(mappedBy = "membersOfEvent")
    private List<Event> events;

    public User() {
    }

    public User(Long id, String username, String password, List<Event> events) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
