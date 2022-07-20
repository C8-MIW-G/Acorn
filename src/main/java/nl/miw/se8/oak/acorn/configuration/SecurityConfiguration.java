package nl.miw.se8.oak.acorn.configuration;

import nl.miw.se8.oak.acorn.service.AcornUserServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * @Author Wicher Vos
 * security config
 */

@Configuration
public class SecurityConfiguration{

    final AcornUserServiceImplementation acornUserServiceImplementation;

    public SecurityConfiguration(AcornUserServiceImplementation acornUserServiceImplementation) {
        this.acornUserServiceImplementation = acornUserServiceImplementation;
    }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests((authorize) -> authorize
                            .antMatchers("/css/**", "/webjars/**").permitAll()
                            .antMatchers("/", "/register").permitAll()
                            .antMatchers("/admin").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form.loginPage("/login").permitAll().and())
                    .logout(logout -> logout.logoutUrl("/logout").permitAll().logoutSuccessUrl("/")
                            .invalidateHttpSession(true).deleteCookies("JSESSIONID"));

        // TODO - Necessary to prevent 403 (forbidden) code when POSTing with AJAX. ASK JOOST ABOUT THIS!
        http.csrf().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(acornUserServiceImplementation);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
