package app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import app.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService
{
    private UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if(userRepository.existsByUsername(username))
            return userRepository.findByUsername(username);
        else
            throw new UsernameNotFoundException("No User Found by this Username");
    }
}