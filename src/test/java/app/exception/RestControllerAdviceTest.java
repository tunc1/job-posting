package app.exception;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestControllerAdviceTest
{
    RestControllerAdvice restControllerAdvice;

    @BeforeEach
    void init()
    {
        restControllerAdvice=new RestControllerAdvice();
    }
    @Test
    void testEntityNotFoundExceptionHandler()
    {
        ExceptionMessage exceptionMessage=restControllerAdvice.entityNotFoundExceptionHandler(new EntityNotFoundException());
        Assertions.assertEquals(exceptionMessage.getMessage(),"No Entity Found by this id");
    }
    @Test
    void testIllegalArgumentExceptionHandler()
    {
        String message="message";
        ExceptionMessage exceptionMessage=restControllerAdvice.illegalArgumentExceptionHandler(new IllegalArgumentException(message));
        Assertions.assertEquals(exceptionMessage.getMessage(),message);
    }
    @Test
    void testUnauthorizedExceptionHandler()
    {
        ExceptionMessage exceptionMessage=restControllerAdvice.unauthorizedExceptionHandler(new UnauthorizedException());
        Assertions.assertEquals(exceptionMessage.getMessage(),"You do not Have a Permission for this Action");
    }
}
