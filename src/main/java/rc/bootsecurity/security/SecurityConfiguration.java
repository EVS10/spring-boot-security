package rc.bootsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserPrincipalDetailsService userPrincipalDetailsService;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/index.html").permitAll() // anybody has access to index.thml
                .antMatchers("/profile/**").authenticated() // only authenticated users have access to any file from the profile folder
                .antMatchers("/admin/index").hasRole("ADMIN") // only users with role ADMIN have access to admin/index
                .antMatchers("/management/index").hasAnyRole("ADMIN", "MANAGER") // only users with role ADMIN or MANAGER have access to management/index
                .antMatchers("/api/public/test1").hasAuthority("ACCESS_TEST1") // only users with authority "ACCESS_TEST1" have access
                .antMatchers("/api/public/test2").hasAuthority("ACCESS_TEST2")
                .antMatchers("api/public/users").hasAuthority("ACCESS_TEST2")
                .and()
                .formLogin()
                .loginPage("/login");
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
