package org.kuzd4niil.testTaskMeetRoom.repositories;

import org.kuzd4niil.testTaskMeetRoom.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-14
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM events where start_date_of_event BETWEEN :startOfWeek " +
                    "AND :endOfWeek ORDER BY start_date_of_event"
    )
    public List<Event> getEvents(@Param("startOfWeek") Date startOfWeek, @Param("endOfWeek") Date endOfWeek);
}