package app.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import app.exception.ConflictException;
import app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserUtilTest
{
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    UserUtil userUtil;
    @BeforeEach
    void init()
    {
        userUtil=new UserUtil(userRepository,passwordEncoder);
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
    @Test
    void testEncodePassword()
    {
        userUtil.encodePassword("password");
        Mockito.verify(passwordEncoder).encode(Mockito.anyString());
    }
    @Test
    void passwordCheck()
    {
        Mockito.when(passwordEncoder.matches(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
        Assertions.assertTrue(userUtil.passwordCheck("encoded","notEncoded"));
    }
}
