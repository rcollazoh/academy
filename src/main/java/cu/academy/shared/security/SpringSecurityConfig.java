package cu.academy.shared.security;

import cu.academy.authentication.AuthenticationService;
import cu.academy.shared.filter.CustomAuthenticationFilter;
import cu.academy.shared.filter.CustomAuthenticationProvider;
import cu.academy.shared.filter.CustomAuthorizationFilter;
import cu.academy.shared.filter.SimpleCORSFilter;
import cu.academy.trace.TraceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final AuthenticationService authenticationService;
    private final TraceService traceService;

    public SpringSecurityConfig(
            CustomAuthenticationProvider customAuthenticationProvider,
            AuthenticationService authenticationService, TraceService traceService) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.authenticationService = authenticationService;
        this.traceService = traceService;
    }

    // Secure the endpoints with JWT authentication
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
                authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),
                authenticationService, traceService
        );
        customAuthenticationFilter.setFilterProcessesUrl("/academy/login");
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 🔥 Esto activa el bean anterior
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/academy/person/**", "/academy/area/**", "/academy/practice/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://pradacademy.com")); // o tu dominio
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Content-Type", "Connection", "Accept", "access_token", "refresh_token"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}