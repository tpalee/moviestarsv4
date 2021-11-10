package nl.lee.moviestars.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;
    private nl.lee.moviestars.security.JwtRequestFilter jwtRequestFilter;

    @Autowired
    WebSecurityConfiguration(DataSource dataSource, nl.lee.moviestars.security.JwtRequestFilter jwtRequestFilter) {
        this.dataSource = dataSource;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, authority FROM authorities AS a WHERE username=?");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .and()
                .authorizeRequests()
                //.antMatchers("/**").permitAll()//DEBUG
                .antMatchers(POST,"/authenticate").permitAll()

                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers(POST,"/users/signup").permitAll()
                .antMatchers(DELETE,"/users/{username}").hasRole("ADMIN")
                .antMatchers(PATCH,"/users/{^[\\w]$}/password").authenticated()
                .antMatchers("/users/{id}/movies").permitAll()//.hasAnyRole("USER","ADMIN")
                .antMatchers("/users/**").hasAnyRole("USER","ADMIN")

                .antMatchers(GET,"/movies/{id}/image/**").permitAll()
                .antMatchers(GET,"/movies/{id}/rating").permitAll()
                .antMatchers(GET,"/movies/search").permitAll()
                .antMatchers(GET,"/movies").permitAll()

                .antMatchers(GET,"/public").permitAll()
                .antMatchers("/users/{id}/movies").hasAnyRole("USER","ADMIN")

                .antMatchers(GET,"/movies/**").permitAll()
                .antMatchers(POST,"/movies/**").hasAnyRole("USER","ADMIN")
                .antMatchers(PUT,"/movies/**").hasAnyRole("USER","ADMIN")
                .antMatchers(PATCH,"/movies/**").hasAnyRole("USER","ADMIN")
                .antMatchers(GET,"/reviews/**").permitAll()
                .antMatchers(PATCH,"/reviews/{id}/badlanguage").permitAll()
                .antMatchers("/reviews/**").hasAnyRole("USER","ADMIN")
                .antMatchers(GET,"/images/**").permitAll()
                .antMatchers("/images/**").hasAnyRole("USER","ADMIN")


                .anyRequest().denyAll()
                .and()
                .csrf().disable()
                .cors().and()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }



}
