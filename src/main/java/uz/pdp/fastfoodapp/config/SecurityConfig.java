package uz.pdp.fastfoodapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.pdp.fastfoodapp.dto.ErrorBodyDTO;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] WHITE_LIST = new String[]{
            "/auth/**",
            "/error",
            "/api-docs",
            "/category/**",
            "/product/**",
            "order/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
    };

    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    @Lazy
    public SecurityConfig(ObjectMapper objectMapper, @Qualifier("customUserDetailsService") UserDetailsService userDetailsService, AccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint authenticationEntryPoint, JwtRequestFilter jwtRequestFilter) {
        this.objectMapper = objectMapper;
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors->cors.configurationSource(configurationSource()));
        http.authorizeHttpRequests((auth)->{
                    auth
                            .requestMatchers(WHITE_LIST).permitAll()
                            .requestMatchers("/**").authenticated();
                }
        );
        http.userDetailsService(userDetailsService);
        http.exceptionHandling((handler)->{
            handler.accessDeniedHandler(accessDeniedHandler);
            handler.authenticationEntryPoint(authenticationEntryPoint);
        });
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(session->{
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        return http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return  (request, response, exception) -> {
            ErrorBodyDTO bodyDto = new ErrorBodyDTO(
                    HttpStatus.FORBIDDEN.value(),
                    request.getRequestURI(),
                    request.getRequestURL().toString(),
                    exception.getClass().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            response.setStatus(403);
            PrintWriter writer = response.getWriter();
            ObjectWriter objectWriter = objectMapper.writer();
            objectWriter.writeValue(writer,bodyDto);

        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, exception) -> {
            ErrorBodyDTO bodyDto = new ErrorBodyDTO(
                    HttpStatus.UNAUTHORIZED.value(),
                    request.getRequestURI(),
                    request.getRequestURL().toString(),
                    exception.getClass().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            response.setStatus(401);
            PrintWriter writer = response.getWriter();
            ObjectWriter objectWriter = objectMapper.writer();
            objectWriter.writeValue(writer,bodyDto);

        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

