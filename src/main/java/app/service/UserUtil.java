package app.service;

import org.springframework.stereotype.Component;

import app.exception.ConflictException;
import app.repository.UserRepository;

@Component
public class UserUtil
{
    private UserRepository userRepository;
    public UserUtil(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    public void throwExceptionIfUsernameConflicts(String username)
	{
		if(userRepository.existsByUsername(username))
			throw new ConflictException("Another user uses this username");
	}
}