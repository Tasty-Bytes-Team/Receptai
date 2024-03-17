package lt.tastybytes.receptaiserver;

import lt.tastybytes.receptaiserver.service.impl.JwtServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.mockito.Mockito.mock;
@TestConfiguration
public class CategoryControllerConfig {

    @Bean
    public JwtServiceImpl jwtService() {
        return mock(JwtServiceImpl.class);


    }
    @TestConfiguration
    @EnableWebSecurity
    static class SecurityConfig {

        @Bean
        public UserDetailsService userDetailsService() {
            return mock(UserDetailsService.class);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager() {
            return mock(AuthenticationManager.class);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorizeRequests ->
                            authorizeRequests.anyRequest().permitAll())
                    .csrf(csrf -> csrf.disable());
            return http.build();
        }
    }
}
