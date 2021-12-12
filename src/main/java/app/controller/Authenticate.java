package app.controller;

import app.response.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.User;
import app.exception.ExceptionMessage;
import app.repository.UserRepository;
import app.security.TokenService;

@RestController
@RequestMapping("/authenticate")
public class Authenticate
{
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserRepository userRepository;
    public Authenticate(AuthenticationManager authenticationManager,TokenService tokenService,UserRepository userRepository)
    {
        this.authenticationManager=authenticationManager;
        this.tokenService=tokenService;
        this.userRepository=userRepository;
    }
    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody User user)
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        try
        {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            User userDetails=userRepository.findByUsername(user.getUsername());
            String token=tokenService.create(userDetails);
            return new ResponseEntity<>(new TokenResponse(token),HttpStatus.OK);
        }
        catch(AuthenticationException e)
        {
            return new ResponseEntity<>(new ExceptionMessage(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }
}