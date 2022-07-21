package org.kuzd4niil.testTaskMeetRoom.exceptions;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-21
 */
public class TimeIsNotAMultipleOfHalfOfHourException extends RuntimeException {
    public TimeIsNotAMultipleOfHalfOfHourException(String message) {
        super(message);
    }
}
