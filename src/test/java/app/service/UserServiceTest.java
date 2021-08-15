package app.service;

import app.entity.User;
import app.exception.UnauthorizedException;
import app.repository.UserRepository;
import app.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{
    @Mock
    UserRepository userRepository;
    @Mock
    UserUtil userUtil;
    UserService userService;
    User user;

    @BeforeEach
    void setUp()
    {
        userService=new UserService(userRepository,userUtil);
        user=new User();
    }
    @Test
    void changePassword_throwsUnauthorizedException()
    {
        user.setUsername("username");
        user.setPassword("password");
        Mockito.when(userUtil.passwordCheck(Mockito.anyString(),Mockito.anyString())).thenReturn(false);
        Assertions.assertThrows(UnauthorizedException.class,()->userService.changePassword(user,"password","newPassword"));
    }
    @Test
    void changePassword_changesPassword()
    {
        String encodedPassword="encoded";
        User user=new User();
        user.setUsername("username");
        user.setPassword("password");
        Mockito.when(userUtil.passwordCheck(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
        Mockito.when(userUtil.encodePassword(Mockito.anyString())).thenReturn(encodedPassword);
        userService.changePassword(user,"password","newPassword");
        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Assertions.assertTrue(user.getPassword().equals(encodedPassword));
    }
    @Test
    void changeUsername()
    {
        userService.changeUsername(user);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }
}