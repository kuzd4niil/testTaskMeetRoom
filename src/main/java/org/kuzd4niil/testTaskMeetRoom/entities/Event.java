package org.kuzd4niil.testTaskMeetRoom.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-13
 */
@Entity
@Table(name = "events")
public class Event implements Serializable {
    @Id
    @SequenceGenerator(
            name = "event_id_sequence",
            sequenceName = "event_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "event_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "event_name",
            nullable = false
    )
    private String eventName;
    @Column(
            name = "description"
    )
    private String description;
    @Column(
            name = "start_date_of_event",
            nullable = false
    )
    private Date startDateOfEvent;
    @Column(
            name = "end_date_of_event",
            nullable = false
    )
    private Date endDateOfEvent;
    @ManyToMany
    @JoinTable(
            name = "members_of_event",
            joinColumns = {@JoinColumn(name = "event_id") },
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> membersOfEvent;

    public Event() {

    }

    public Event(Long id, String eventName, String description, Date startDateOfEvent, Date endDateOfEvent, List<User> membersOfEvent) {
        this.id = id;
        this.eventName = eventName;
        this.description = description;
        this.startDateOfEvent = startDateOfEvent;
        this.endDateOfEvent = endDateOfEvent;
        this.membersOfEvent = membersOfEvent;
    }

    public Long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDateOfEvent() {
        return startDateOfEvent;
    }

    public void setStartDateOfEvent(Date startDateOfEvent) {
        this.startDateOfEvent = startDateOfEvent;
    }

    public Date getEndDateOfEvent() {
        return endDateOfEvent;
    }

    public void setEndDateOfEvent(Date endDateOfEvent) {
        this.endDateOfEvent = endDateOfEvent;
    }

    public List<User> getMembersOfEvent() {
        return membersOfEvent;
    }

    public void setMembersOfEvent(List<User> membersOfEvent) {
        this.membersOfEvent = membersOfEvent;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", startDateOfEvent=" + startDateOfEvent +
                ", endDateOfEvent=" + endDateOfEvent +
                ", membersOfEvent=" + membersOfEvent +
                '}';
    }
}