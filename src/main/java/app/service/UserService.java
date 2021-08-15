package app.service;

import app.entity.User;
import app.exception.UnauthorizedException;
import app.repository.UserRepository;
import app.util.UserUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private UserRepository userRepository;
    private UserUtil userUtil;
    public UserService(UserRepository userRepository,UserUtil userUtil)
    {
        this.userRepository=userRepository;
        this.userUtil=userUtil;
    }
    public void changePassword(User user,String currentPassword,String newPassword)
    {
        if(userUtil.passwordCheck(user.getPassword(),currentPassword))
        {
            user.setPassword(userUtil.encodePassword(newPassword));
            userRepository.save(user);
        }
        else
            throw new UnauthorizedException();
    }
    public void changeUsername(User user)
    {
        userRepository.save(user);
    }
}