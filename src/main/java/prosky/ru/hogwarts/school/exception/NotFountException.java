package prosky.ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFountException extends RuntimeException {

    public NotFountException(String message) {
        super(message);
    }

}
