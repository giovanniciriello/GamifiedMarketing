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
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/leaderboard").hasRole("ADMIN")
                .antMatchers("/home").hasRole("USER")
                .antMatchers("/").permitAll()
                .and().formLogin()
                .loginPage("/login.html");

//        http.logout().logoutUrl("/my/logout")
//                .logoutSuccessUrl("/my/index")
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .invalidateHttpSession(true)
//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear)
//                .and()
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}