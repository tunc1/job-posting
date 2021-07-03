package app.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice
{
    @ExceptionHandler(value={EntityNotFoundException.class})
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ExceptionMessage entityNotFoundExceptionHandler(Exception e)
    {
        return new ExceptionMessage("No Entity Found by this id");
    }
    @ExceptionHandler(value={Exception.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage exceptionHandler(Exception e)
    {
        e.printStackTrace();
        return new ExceptionMessage("Something Went Wrong");
    }
}