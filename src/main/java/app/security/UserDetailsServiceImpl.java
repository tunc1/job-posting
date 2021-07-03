package app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import app.service.MemberService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService
{
    private MemberService memberService;
    public UserDetailsServiceImpl(MemberService memberService)
    {
        this.memberService=memberService;
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if(memberService.existsByUsername(username))
            return memberService.findByUsername(username);
        else
            throw new UsernameNotFoundException("No User Found by this Username");
    }
}