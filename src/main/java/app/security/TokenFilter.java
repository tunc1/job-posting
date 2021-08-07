package app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import app.entity.Admin;
import app.entity.Manager;
import app.entity.Member;
import app.entity.Role;
import app.repository.ManagerRepository;
import app.repository.UserRepository;
import app.service.AdminService;
import app.service.MemberService;


@Component
public class TokenFilter extends OncePerRequestFilter
{
    private TokenService tokenService;
    private UserRepository userRepository;
    private MemberService memberService;
    private ManagerRepository managerRepository;
    private AdminService adminService;
    public TokenFilter(TokenService tokenService, UserRepository userRepository, MemberService memberService,
            ManagerRepository managerRepository,AdminService adminService)
    {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.memberService = memberService;
        this.managerRepository = managerRepository;
        this.adminService = adminService;
    }
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException
    {
        String authorization=request.getHeader("Authorization");
        if(authorization!=null)
        {
            if(authorization.contains("Bearer "))
            {
                String token=authorization.split("Bearer ")[1];
                if(tokenService.validate(token))
                {
                    String username=tokenService.get(token,"username");
                    if(userRepository.existsByUsername(username))
                    {
                        String role=tokenService.get(token,"role");
                        if(role.equals(Role.MEMBER))
                        {
                            Member member=memberService.findByUserUsername(username);
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(member,null,member.getUser().getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        }
                        else if(role.equals(Role.MANAGER))
                        {
                            Manager manager=managerRepository.findByUserUsername(username);
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(manager,null,manager.getUser().getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        }
                        else if(role.equals(Role.ADMIN))
                        {
                            Admin admin=adminService.findByUserUsername(username);
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(admin,null,admin.getUser().getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        }
                    }
                }
            }
        }
        if(filterChain!=null)
            filterChain.doFilter(request,response);
    }
}