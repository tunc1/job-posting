package app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userDetails")
public class UserDetails
{
    @GetMapping
    public Object userDetails(Authentication authentication)
    {
        return authentication.getPrincipal();
    }
}