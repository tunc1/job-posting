package app.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import app.exception.ConflictException;
import app.repository.UserRepository;

@Component
public class UserUtil
{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public UserUtil(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void throwExceptionIfUsernameConflicts(String username)
	{
		if(userRepository.existsByUsername(username))
			throw new ConflictException("Another user uses this username");
	}
    public String encodePassword(String password)
    {
        return passwordEncoder.encode(password);
    }
}