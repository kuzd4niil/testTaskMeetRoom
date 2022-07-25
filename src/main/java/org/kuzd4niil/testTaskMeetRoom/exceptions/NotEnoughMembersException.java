package org.kuzd4niil.testTaskMeetRoom.exceptions;

/**
 * @author : daniil
 * @description :
 * @create : 2022-07-24
 */
public class NotEnoughMembersException extends RuntimeException {
    public NotEnoughMembersException(String message) {
        super(message);
    }
}
