package it.polimi.db2.gamifiedmarketing.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String usersQuery = "SELECT email, password, 1 FROM users WHERE email = ?";
    private final String rolesQuery = "SELECT email, role FROM users WHERE email = ?";

    @Autowired
    private DataSource dataSource;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()

                    // The HTTP parameter to look for the username when performing authentication.
                    .usernameParameter("email")

                    // The HTTP parameter to look for the password when performing authentication.
                    .passwordParameter("password")

                    // The URL to send users if authentication fails
                    .failureUrl("/login?error")

                    // Specifies where users will go after authenticating successfully if they have not visited a secured page prior to authenticating.
                    .defaultSuccessUrl("/home")

                    // Specifies the URL to send users to if login is required.
                    .loginPage("/login.html")
                    .permitAll()
                    .and()

                .logout() // The default URL that triggers log out to occur is "/logout".

                    // The URL to redirect to after logout has occurred.
                    .logoutSuccessUrl("/login")

                    // Configures SecurityContextLogoutHandler to invalidate the HttpSession at the time of logout.
                    .invalidateHttpSession(true)

                    // Specifies if SecurityContextLogoutHandler should clear the Authentication at the time of logout.
                    .clearAuthentication(true);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}