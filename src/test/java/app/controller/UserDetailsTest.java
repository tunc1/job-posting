package app.controller;

import app.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsTest
{
    Authentication authentication;
    UserDetails userDetails;
    Member member;
    @BeforeEach
    void init()
    {
        userDetails=new UserDetails();
        member=new Member();
        authentication=new UsernamePasswordAuthenticationToken(member,null);
    }
    @Test
    void userDetails()
    {
        Object actual=userDetails.userDetails(authentication);
        assertEquals(actual,member);
    }
}