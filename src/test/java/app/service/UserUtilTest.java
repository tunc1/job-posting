package app.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import app.exception.ConflictException;
import app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
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
