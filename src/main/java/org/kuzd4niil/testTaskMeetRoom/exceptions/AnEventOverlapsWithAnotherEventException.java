package org.kuzd4niil.testTaskMeetRoom.exceptions;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-21
 */
public class AnEventOverlapsWithAnotherEventException extends RuntimeException {
    public AnEventOverlapsWithAnotherEventException(String message) {
        super(message);
    }
}
