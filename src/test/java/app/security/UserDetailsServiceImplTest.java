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

import app.entity.Member;
import app.service.MemberService;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest
{
    @Mock
    MemberService memberService;
    UserDetailsService userDetailsService;

    @BeforeEach
    void init()
    {
        userDetailsService=new UserDetailsServiceImpl(memberService);
    }
    @Test
    void testLoadUserByUsername_returnsUserDetails()
    {
        Member member=new Member();
        Mockito.when(memberService.findByUsername(Mockito.anyString())).thenReturn(member);
        Mockito.when(memberService.existsByUsername(Mockito.anyString())).thenReturn(true);
        UserDetails userDetails=userDetailsService.loadUserByUsername("username");
        Assertions.assertEquals(member,userDetails);
    }
    @Test
    void testLoadUserByUsername_throwsUsernameNotFoundException()
    {
        Mockito.when(memberService.existsByUsername(Mockito.anyString())).thenReturn(false);
        Assertions.assertThrows(UsernameNotFoundException.class,()->userDetailsService.loadUserByUsername("username"));
    }
}
