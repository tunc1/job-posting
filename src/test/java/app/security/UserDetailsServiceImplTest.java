package app.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import app.entity.User;
import app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest
{
    @Mock
    UserRepository userRepository;
    UserDetailsService userDetailsService;

    @BeforeEach
    void init()
    {
        userDetailsService=new UserDetailsServiceImpl(userRepository);
    }
    @Test
    void testLoadUserByUsername_returnsUserDetails()
    {
        User user=new User();
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        UserDetails userDetails=userDetailsService.loadUserByUsername("username");
        Assertions.assertEquals(user,userDetails);
    }
    @Test
    void testLoadUserByUsername_throwsUsernameNotFoundException()
    {
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Assertions.assertThrows(UsernameNotFoundException.class,()->userDetailsService.loadUserByUsername("username"));
    }
}
