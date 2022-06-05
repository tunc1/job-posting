package app.security;

import app.consts.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig
{
    @Autowired
    private TokenFilter tokenFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth.antMatchers(HttpMethod.POST,"/authenticate","/member").permitAll()
                        .antMatchers(HttpMethod.POST,"/member/*/language","/member/*/education","/member/*/experience").hasRole(Role.MEMBER)
                        .antMatchers(HttpMethod.PUT,"/member/*/language/*","/member/*/education/*","/member/*/experience/*").hasRole(Role.MEMBER)
                        .antMatchers(HttpMethod.DELETE,"/member/*/language/*","/member/*/education/*","/member/*/experience/*").hasRole(Role.MEMBER)
                        .antMatchers(HttpMethod.DELETE,"/jobPosting/**").hasRole(Role.MANAGER)
                        .antMatchers(HttpMethod.POST,"/jobPosting/**").hasRole(Role.MANAGER)
                        .antMatchers(HttpMethod.PUT,"/jobPosting/**").hasRole(Role.MANAGER)
                        .antMatchers(HttpMethod.PUT,"/member/**").hasRole(Role.MEMBER)
                        .antMatchers(HttpMethod.DELETE,"/member/**").hasRole(Role.MEMBER)
                        .antMatchers(HttpMethod.DELETE,"/company/**","/skill/**","/language/**","/country/**","/city/**","/university/**","/major/**").hasRole(Role.ADMIN)
                        .antMatchers(HttpMethod.PUT,"/company/**","/skill/**","/language/**","/country/**","/city/**","/university/**","/major/**").hasRole(Role.ADMIN)
                        .antMatchers(HttpMethod.POST,"/company/**","/skill/**","/language/**","/country/**","/city/**","/university/**","/major/**").hasRole(Role.ADMIN)
                        .antMatchers("/appliedJob/**").hasRole(Role.MEMBER)
                        .antMatchers("/manager/**").hasRole(Role.ADMIN)
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(tokenFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }
}