package app.controller;

import app.entity.IUser;
import app.entity.User;
import app.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class UserControllerTest
{
    @Mock
    UserService userService;
    UserController userController;
    Authentication authentication;
    User user;

    @BeforeEach
    void setUp()
    {
        userController=new UserController(userService);
        this.user=new User();
        IUser user=()->this.user;
        authentication=new UsernamePasswordAuthenticationToken(user,null);
    }
    @Test
    void changePassword()
    {
        PasswordHolder passwordHolder=new PasswordHolder();
        passwordHolder.setNewPassword("newPassword");
        passwordHolder.setCurrentPassword("currentPassword");
        userController.changePassword(authentication,passwordHolder);
        Mockito.verify(userService).changePassword(Mockito.eq(user)
                ,Mockito.eq(passwordHolder.getCurrentPassword())
                ,Mockito.eq(passwordHolder.getNewPassword()));
    }
    @Test
    void changeUsername()
    {
        UsernameHolder usernameHolder=new UsernameHolder();
        usernameHolder.setUsername("username");
        userController.changeUsername(authentication,usernameHolder);
        Mockito.verify(userService).changeUsername(Mockito.eq(user));
        Assertions.assertTrue(usernameHolder.getUsername().equals(user.getUsername()));
    }
}