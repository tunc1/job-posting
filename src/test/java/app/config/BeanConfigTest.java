package app.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BeanConfigTest
{
    @Test
    void testPasswordEncoder()
    {
        BeanConfig beanConfig=new BeanConfig();
        PasswordEncoder passwordEncoder=beanConfig.passwordEncoder();
        Assertions.assertEquals(passwordEncoder.getClass(),BCryptPasswordEncoder.class);
    }
}
