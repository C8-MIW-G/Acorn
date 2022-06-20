package nl.miw.se8.oak.acorn.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Author Wicher Vos feat. Thijs
 * security config
 *
 */

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/css/**", "/webjars/**").permitAll()
                        .antMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().and()
                .logout().logoutSuccessUrl("/");
        return http.build();
    }

}
