package com.blog.config;

import com.blog.security.CustomUserDetailService;
import com.blog.security.JWTAuthenticationEntryPoint;
import com.blog.security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConf {

//
//      WebSecurityConfigurerAdapter is  deprecated
//      extends WebSecurityConfigurerAdapter {

    public static final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",
            "/v3/api-docs/",
            "/v3/api-docs/**",
            "/v2/api-docs/",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "**/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"
    };

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    //    new method added
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(PUBLIC_URLS).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authenticationProvider(daoAuthenticationProvider());

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
        return defaultSecurityFilterChain;
    }

    /**
     * basic authentication
     * http
     * .csrf().disable()
     * .authorizeHttpRequests()
     * .anyRequest()
     * .authenticated()
     * .and()
     * .httpBasic();
     */

    /**
     * This is commented as WebSecurityConfigurerAdapter is deprecated
     *
     * @Override protected void configure(HttpSecurity http) throws Exception {
     * http
     * .csrf().disable()
     * .authorizeHttpRequests()
     * .antMatchers(PUBLIC_URLS).permitAll()
     * .anyRequest()
     * .authenticated()
     * .and()
     * .exceptionHandling()
     * .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
     * .and()
     * .sessionManagement()
     * .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
     * http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
     * }
     *
     *
     *@Override
     *protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     *auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
     *}
     *
     * @Bean
     * @Override public AuthenticationManager authenticationManagerBean() throws Exception {
     * return super.authenticationManagerBean();
     * }
     */



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
