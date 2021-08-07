package app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import app.exception.ConflictException;
import app.repository.UserRepository;

public class UserUtilTest
{
    @Mock
    UserRepository userRepository;
    UserUtil userUtil;
    @BeforeEach
    void init()
    {
        userUtil=new UserUtil(userRepository);
    }
    @Test
    void testThrowExceptionIfUsernameConflicts_throwsConflictException()
    {
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        Assertions.assertThrows(ConflictException.class,()->userUtil.throwExceptionIfUsernameConflicts("username"));
    }
    @Test
    void testThrowExceptionIfUsernameConflicts_notThrowsConflictException()
    {
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Assertions.assertDoesNotThrow(()->userUtil.throwExceptionIfUsernameConflicts("username"));
    }
}
