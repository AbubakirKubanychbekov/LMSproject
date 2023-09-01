package boss.exception.globalHandlerException;

import boss.exception.ExceptionResponse;
import boss.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ExceptionResponse notFound(NotFoundException n){
        return new ExceptionResponse(HttpStatus.FOUND,n.getClass().getSimpleName(),n.getMessage());
    }

}
