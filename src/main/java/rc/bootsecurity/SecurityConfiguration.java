package rc.bootsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
                .and()
                .withUser("slava").password(passwordEncoder().encode("slava123")).roles("USER")
                .and()
                .withUser("manager").password(passwordEncoder().encode("manager123")).roles("MANAGER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/index.html").permitAll() // anybody has access to index.thml
                .antMatchers("/profile/**").authenticated() // only authenticated users have access to any file from the profile folder
                .antMatchers("/admin/index").hasRole("ADMIN") // only users with role ADMIN have access to admin/index
                .antMatchers("/management/index").hasAnyRole("ADMIN", "MANAGER") // only users with role ADMIN or MANAGER have access to management/index
                .antMatchers("/api/public/**").hasRole("ADMIN") // protecting REST controller
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
