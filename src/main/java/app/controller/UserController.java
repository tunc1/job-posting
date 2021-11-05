package app.controller;

import app.entity.IUser;
import app.entity.User;
import app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController
{
    private UserService userService;
    public UserController(UserService userService)
    {
        this.userService=userService;
    }
    @PostMapping("/change-password")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void changePassword(Authentication authentication,@RequestBody PasswordHolder passwordHolder)
    {
        User user=((IUser)authentication.getPrincipal()).getUser();
        userService.changePassword(user,passwordHolder.getCurrentPassword(),passwordHolder.getNewPassword());
    }
    @PostMapping("/change-username")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void changeUsername(Authentication authentication,@RequestBody UsernameHolder usernameHolder)
    {
        User user=((IUser)authentication.getPrincipal()).getUser();
        user.setUsername(usernameHolder.getUsername());
        userService.changeUsername(user);
    }
}