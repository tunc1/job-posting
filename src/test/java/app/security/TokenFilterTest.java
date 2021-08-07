package app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import app.entity.Manager;
import app.entity.Member;
import app.entity.Role;
import app.entity.User;
import app.repository.ManagerRepository;
import app.repository.UserRepository;
import app.service.MemberService;

@ExtendWith(MockitoExtension.class)
public class TokenFilterTest
{
    @Mock
    TokenService tokenService;
    @Mock
    MemberService memberService;
    @Mock
    UserRepository userRepository;
    @Mock
    ManagerRepository managerRepository;
    @Mock
    HttpServletRequest request;
    TokenFilter tokenFilter;
    @BeforeEach
    void init()
    {
        tokenFilter=new TokenFilter(tokenService,userRepository,memberService,managerRepository);
    }
    @Test
    void testDoFilterInternal_noAuthorizationHeader() throws ServletException,IOException
    {
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn(null);
        Assertions.assertDoesNotThrow(()->tokenFilter.doFilterInternal(request,null,null));
    }
    @Test
    void testDoFilterInternal_noBearer() throws ServletException,IOException
    {
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn("Token");
        Assertions.assertDoesNotThrow(()->tokenFilter.doFilterInternal(request,null,null));
    }
    @Test
    void testDoFilterInternal_notValid() throws ServletException,IOException
    {
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn("Bearer Token");
        Mockito.when(tokenService.validate(Mockito.anyString())).thenReturn(false);
        tokenFilter.doFilterInternal(request,null,null);
        Mockito.verify(tokenService,Mockito.times(0)).get(Mockito.anyString(),Mockito.eq("username"));
    }
    @Test
    void testDoFilterInternal_userNotExists() throws ServletException,IOException
    {
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn("Bearer Token");
        Mockito.when(tokenService.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(tokenService.get(Mockito.anyString(),Mockito.eq("username"))).thenReturn("username");
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        tokenFilter.doFilterInternal(request,null,null);
        Mockito.verify(tokenService,Mockito.times(0)).get(Mockito.anyString(),Mockito.eq("role"));
    }
    @Test
    void testDoFilterInternal_memberExists() throws ServletException,IOException
    {
        Member member=new Member();
        User user=new User();
        user.setRole(Role.MEMBER);
        member.setUser(user);
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn("Bearer Token");
        Mockito.when(tokenService.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        Mockito.when(tokenService.get(Mockito.anyString(),Mockito.eq("username"))).thenReturn("username");
        Mockito.when(tokenService.get(Mockito.anyString(),Mockito.eq("role"))).thenReturn(Role.MEMBER);
        Mockito.when(memberService.findByUserUsername(Mockito.anyString())).thenReturn(member);
        tokenFilter.doFilterInternal(request,null,null);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertEquals(authentication.getPrincipal(),member);
        Assertions.assertEquals(authentication.getAuthorities(),member.getUser().getAuthorities());
    }
    @Test
    void testDoFilterInternal_managerExists() throws ServletException,IOException
    {
        Manager manager=new Manager();
        User user=new User();
        user.setRole(Role.MANAGER);
        manager.setUser(user);
        Mockito.when(request.getHeader(Mockito.eq("Authorization"))).thenReturn("Bearer Token");
        Mockito.when(tokenService.validate(Mockito.anyString())).thenReturn(true);
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        Mockito.when(tokenService.get(Mockito.anyString(),Mockito.eq("username"))).thenReturn("username");
        Mockito.when(tokenService.get(Mockito.anyString(),Mockito.eq("role"))).thenReturn(Role.MANAGER);
        Mockito.when(managerRepository.findByUserUsername(Mockito.anyString())).thenReturn(manager);
        tokenFilter.doFilterInternal(request,null,null);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertEquals(authentication.getPrincipal(),manager);
        Assertions.assertEquals(authentication.getAuthorities(),manager.getUser().getAuthorities());
    }
}
