package app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private TokenFilter tokenFilter;
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/authenticate","/member").permitAll()
                .antMatchers(HttpMethod.DELETE,"/jobPosting/**").hasRole("MANAGER")
                .antMatchers(HttpMethod.POST,"/jobPosting/**").hasRole("MANAGER")
                .antMatchers(HttpMethod.PUT,"/jobPosting/**").hasRole("MANAGER")
                .antMatchers(HttpMethod.PUT,"/member/**").hasRole("MEMBER")
                .antMatchers(HttpMethod.DELETE,"/member/**").hasRole("MEMBER")
                .antMatchers("/appliedJob/**").hasRole("MEMBER")
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(tokenFilter,UsernamePasswordAuthenticationFilter.class);
    }
}