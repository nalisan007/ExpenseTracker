package io.expensetracker.ExpenseTracker.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    UserDetailsService myUserDetailsService;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,AuthenticationManager authManager) throws Exception {
        return  http.
                csrf(customizer-> customizer.disable())
                .authorizeHttpRequests(
                        request->request
                                .requestMatchers("auth/login","auth/login/","/register","/register/","/h2-console/**","/").permitAll()
//                                .requestMatchers("deleteExpiredToken").access(new WebExpressionAuthorizationManager("authentication.name =='admin@admin.com'"))
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .logoutSuccessHandler((req, res, authentication) -> {
//                            res.setStatus(HttpServletResponse.SC_OK);
//                            res.getWriter().write("Logged out");
//                        })

//                )
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }


@Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(myUserDetailsService);
        return provider;


    }





}
