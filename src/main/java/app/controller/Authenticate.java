package app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Member;
import app.exception.ExceptionMessage;
import app.security.TokenService;
import app.service.MemberService;

@RestController
@RequestMapping("/authenticate")
public class Authenticate
{
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private MemberService memberService;
    public Authenticate(AuthenticationManager authenticationManager,TokenService tokenService,MemberService memberService)
    {
        this.authenticationManager=authenticationManager;
        this.tokenService=tokenService;
        this.memberService=memberService;
    }
    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody Member member)
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(member.getUsername(),member.getPassword());
        try
        {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            UserDetails userDetails=memberService.findByUsername(member.getUsername());
            String token=tokenService.create(userDetails);
            return new ResponseEntity<>(new TokenResponse(token),HttpStatus.OK);
        }
        catch(AuthenticationException e)
        {
            return new ResponseEntity<>(new ExceptionMessage(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }
}
class TokenResponse
{
    private String token;
    public TokenResponse(String token)
    {
        this.token=token;
    }
    public String getToken()
    {
        return token;
    }
    public void setToken(String token)
    {
        this.token=token;
    }
}