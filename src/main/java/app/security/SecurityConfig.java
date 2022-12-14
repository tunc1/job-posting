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
                        auth.requestMatchers(HttpMethod.POST,"/authenticate","/member").permitAll()
                        .requestMatchers(HttpMethod.POST,"/member/*/language","/member/*/education","/member/*/experience").hasRole(Role.MEMBER)
                        .requestMatchers(HttpMethod.PUT,"/member/*/language/*","/member/*/education/*","/member/*/experience/*").hasRole(Role.MEMBER)
                        .requestMatchers(HttpMethod.DELETE,"/member/*/language/*","/member/*/education/*","/member/*/experience/*").hasRole(Role.MEMBER)
                        .requestMatchers(HttpMethod.DELETE,"/jobPosting/**").hasRole(Role.MANAGER)
                        .requestMatchers(HttpMethod.POST,"/jobPosting/**").hasRole(Role.MANAGER)
                        .requestMatchers(HttpMethod.PUT,"/jobPosting/**").hasRole(Role.MANAGER)
                        .requestMatchers(HttpMethod.PUT,"/member/**").hasRole(Role.MEMBER)
                        .requestMatchers(HttpMethod.DELETE,"/member/**").hasRole(Role.MEMBER)
                        .requestMatchers(HttpMethod.DELETE,"/company/**","/skill/**","/language/**","/country/**","/city/**","/university/**","/major/**").hasRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.PUT,"/company/**","/skill/**","/language/**","/country/**","/city/**","/university/**","/major/**").hasRole(Role.ADMIN)
                        .requestMatchers(HttpMethod.POST,"/company/**","/skill/**","/language/**","/country/**","/city/**","/university/**","/major/**").hasRole(Role.ADMIN)
                        .requestMatchers("/appliedJob/**").hasRole(Role.MEMBER)
                        .requestMatchers("/manager/**").hasRole(Role.ADMIN)
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