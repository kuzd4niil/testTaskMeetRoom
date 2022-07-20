package org.kuzd4niil.testTaskMeetRoom.repositories;

import org.kuzd4niil.testTaskMeetRoom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-14
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}