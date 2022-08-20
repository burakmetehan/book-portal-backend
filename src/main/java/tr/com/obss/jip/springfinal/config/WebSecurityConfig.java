package tr.com.obss.jip.springfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tr.com.obss.jip.springfinal.filter.JwtRequestFilter;

import static javax.servlet.http.HttpServletResponse.*;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserDetailsService jwtUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            UserDetailsService jwtUserDetailsService,
            JwtRequestFilter jwtRequestFilter,
            PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        //http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // Set unauthorized requests exception handler
        /*http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
            response.sendError(SC_UNAUTHORIZED, ex.getMessage());
        }).and();*/

        http = http.authorizeHttpRequests().antMatchers("/authenticate").permitAll().anyRequest()
                   //.hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                   .permitAll().and();


        // Set unauthorized and forbidden requests exception handler
        http = http.exceptionHandling()
                   .accessDeniedHandler((req, resp, ex) -> resp.setStatus(SC_FORBIDDEN)) // if someone tries to access protected resource but doesn't have enough permissions
                   .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                   .and();

        // Login and logout
        /*http.formLogin()
            .loginProcessingUrl("/login")
            .successHandler((req, resp, auth) -> resp.setStatus(SC_OK)) // success authentication
            .failureHandler((req, resp, ex) -> resp.setStatus(SC_FORBIDDEN))
            .and()
            .logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID");*/

        /*
                .formLogin()
            .loginPage("/login")
            *//*.loginProcessingUrl("/login")*//*.and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            *//*.successHandler((req, resp, auth) -> resp.setStatus(SC_OK)) // success authentication
            .failureHandler((req, resp, ex) -> resp.setStatus(SC_FORBIDDEN))*//*
         *//*.defaultSuccessUrl("/homepage.html", true)
            .and()
            .logout()
            .logoutUrl("/logout")
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
            .and()*//*.and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/

        // Add JWT token filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /*@Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/homepage.html", true)
                .failureUrl("/login.html?error=true")
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler());




                 //.authenticationEntryPoint((req, resp, ex) -> resp.setStatus(SC_UNAUTHORIZED));
    }*/

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .anyRequest()
            .hasAnyRole("ADMIN", "USER")
            .and()
            .exceptionHandling()
            .accessDeniedHandler((req, resp, ex) -> resp.setStatus(SC_FORBIDDEN)) // if someone tries to access protected resource but doesn't have enough permissions
            .authenticationEntryPoint((req, resp, ex) -> resp.setStatus(SC_UNAUTHORIZED))
            .and()
            .formLogin()
            .loginPage("")
            .loginProcessingUrl("/login")
            .successHandler((req, resp, auth) -> resp.setStatus(SC_OK)) // success authentication
            .failureHandler((req, resp, ex) -> resp.setStatus(SC_FORBIDDEN))
            .and() // bad credentials
            .sessionManagement()
            .invalidSessionStrategy((req, resp) -> resp.setStatus(SC_UNAUTHORIZED))
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
            .and()
            .csrf()
            .disable();
    }*/


    // Set permissions on endpoints
        /*http.authorizeRequests()
            // Our public endpoints
            .antMatchers("/authenticate").permitAll()
            *//*.antMatchers(HttpMethod.GET, "/api/author/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/author/search").permitAll()
            .antMatchers(HttpMethod.GET, "/api/book/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/book/search").permitAll()*//*
            .antMatchers("/users/*").hasRole("ADMIN")
            // Our private endpoints
            .anyRequest().authenticated();*/

        /*http.authorizeRequests()
            .antMatchers("/authenticate")
            .permitAll() // do not authenticate this particular request
            .anyRequest()
            .authenticated()
            .and() // all other requests need to be authenticated
            .formLogin()
            .loginPage("/login")
            *//*.loginProcessingUrl("/login")*//*.and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            *//*.successHandler((req, resp, auth) -> resp.setStatus(SC_OK)) // success authentication
            .failureHandler((req, resp, ex) -> resp.setStatus(SC_FORBIDDEN))*//*
     *//*.defaultSuccessUrl("/homepage.html", true)
            .and()
            .logout()
            .logoutUrl("/logout")
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
            .and()*//*.and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/

    // Add a filter to validate the tokens with every request

}
