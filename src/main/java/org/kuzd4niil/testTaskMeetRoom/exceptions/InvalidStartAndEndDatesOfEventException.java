package org.kuzd4niil.testTaskMeetRoom.exceptions;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-21
 */
public class InvalidStartAndEndDatesOfEventException extends RuntimeException{
    public InvalidStartAndEndDatesOfEventException(String message) {
        super(message);
    }
}
