package org.kuzd4niil.testTaskMeetRoom.exceptions;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-21
 */
public class StartTimeAndEndTimeOfEventIsTheSameException extends RuntimeException{
    public StartTimeAndEndTimeOfEventIsTheSameException(String message) {
        super(message);
    }
}
