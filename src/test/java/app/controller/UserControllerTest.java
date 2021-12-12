package app.controller;

import app.entity.IUser;
import app.entity.User;
import app.request.ChangePasswordRequest;
import app.request.ChangeUsernameRequest;
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
        ChangePasswordRequest changePasswordRequest=new ChangePasswordRequest();
        changePasswordRequest.setNewPassword("newPassword");
        changePasswordRequest.setCurrentPassword("currentPassword");
        userController.changePassword(authentication,changePasswordRequest);
        Mockito.verify(userService).changePassword(Mockito.eq(user)
                ,Mockito.eq(changePasswordRequest.getCurrentPassword())
                ,Mockito.eq(changePasswordRequest.getNewPassword()));
    }
    @Test
    void changeUsername()
    {
        ChangeUsernameRequest changeUsernameRequest=new ChangeUsernameRequest();
        changeUsernameRequest.setUsername("username");
        userController.changeUsername(authentication,changeUsernameRequest);
        Mockito.verify(userService).changeUsername(Mockito.eq(user));
        Assertions.assertTrue(changeUsernameRequest.getUsername().equals(user.getUsername()));
    }
}