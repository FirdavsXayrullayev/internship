package uz.intership.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.intership.dto.ResponseDto;
import uz.intership.securityJwt.JwtFilter;
import uz.intership.servise.UserService;
import uz.intership.servise.impl.UserServiceImpl;

@Configuration
@EnableMethodSecurity
//@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private Gson gson;

    @Autowired
//    @Lazy
    private UserServiceImpl userServiceImpl;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder aut) throws Exception {
        aut.authenticationProvider(authenticationProvider());
    }

    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userServiceImpl);
        return provider;
    }
    private CorsConfigurationSource corsConfiguration(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfiguration());
        })
                .authorizeHttpRequests(req ->{
                    req.requestMatchers(HttpMethod.POST,"/user/**")
                            .permitAll()
                            .requestMatchers("/swagger-ui/**","/v3/api-docs/**", "/swagger-ui.html")
                            .permitAll()
//                            .requestMatchers(HttpMethod.POST,"/user/login")
//                            .hasAuthority("user")
                            .anyRequest()
                            .authenticated();


                })
                .csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
