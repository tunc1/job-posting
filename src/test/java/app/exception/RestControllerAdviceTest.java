package app.exception;

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
        ExceptionMessage exceptionMessage=restControllerAdvice.entityNotFoundExceptionHandler(new RuntimeException("message"));
        Assertions.assertEquals(exceptionMessage.getMessage(),"No Entity Found by this id");
    }
    @Test
    void testExceptionHandler()
    {
        ExceptionMessage exceptionMessage=restControllerAdvice.exceptionHandler(new RuntimeException("message"));
        Assertions.assertEquals(exceptionMessage.getMessage(),"Something Went Wrong");
    }
}
