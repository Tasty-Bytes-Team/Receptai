package lt.tastybytes.receptaiserver;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lt.tastybytes.receptaiserver.service.impl.JwtServiceImpl;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class RecipeControllerConfig {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return mock(UserDetailsService.class);
    }

    @Bean
    public JwtServiceImpl jwtService() {
        return mock(JwtServiceImpl.class);
    }
}
