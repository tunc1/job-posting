package app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import app.service.MemberService;

@Component
public class TokenFilter extends OncePerRequestFilter
{
    private TokenService tokenService;
    private MemberService memberService;
    public TokenFilter(TokenService tokenService, MemberService memberService)
    {
        this.tokenService = tokenService;
        this.memberService = memberService;
    }
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException
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
                    UserDetails userDetails=memberService.findByUsername(username);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}