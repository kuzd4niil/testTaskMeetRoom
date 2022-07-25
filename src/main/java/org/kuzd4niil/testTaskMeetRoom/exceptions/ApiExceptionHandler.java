package org.kuzd4niil.testTaskMeetRoom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author :daniil
 * @description :
 * @create :2022-07-21
 */
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = TimeIsNotAMultipleOfHalfOfHourException.class)
    public ResponseEntity<ApiException> handleTimeIsNotAMultipleOfHalfOfHourException(TimeIsNotAMultipleOfHalfOfHourException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
        );

        return new ResponseEntity(apiException, badRequest);
    }

    @ExceptionHandler(value = AnEventOverlapsWithAnotherEventException.class)
    public ResponseEntity<ApiException> handleAnEventOverlapsWithAnotherEventException(AnEventOverlapsWithAnotherEventException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
        );

        return new ResponseEntity(apiException, badRequest);
    }

    @ExceptionHandler(value = InvalidStartAndEndDatesOfEventException.class)
    public ResponseEntity<ApiException> handleStartTimeAndEndTimeOfEventIsTheSameException(InvalidStartAndEndDatesOfEventException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
        );

        return new ResponseEntity(apiException, badRequest);
    }

    @ExceptionHandler(value = NotEnoughMembersException.class)
    public ResponseEntity<ApiException> handleNotEnoughMembersException(NotEnoughMembersException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
        );

        return new ResponseEntity(apiException, badRequest);
    }

}
