package app.exception;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice
{
    @ExceptionHandler(value={EntityNotFoundException.class})
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ExceptionMessage entityNotFoundExceptionHandler(EntityNotFoundException e)
    {
        return new ExceptionMessage("No Entity Found by this id");
    }
    @ExceptionHandler(value={UnauthorizedException.class})
    @ResponseStatus(value=HttpStatus.FORBIDDEN)
    public ExceptionMessage unauthorizedExceptionHandler(UnauthorizedException e)
    {
        return new ExceptionMessage("You do not Have a Permission for this Action");
    }
    @ExceptionHandler(value={IllegalArgumentException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ExceptionMessage illegalArgumentExceptionHandler(IllegalArgumentException e)
    {
        return new ExceptionMessage(e.getMessage());
    }
}