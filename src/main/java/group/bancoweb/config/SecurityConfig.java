package group.bancoweb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuracion principal de seguridad de la aplicacion
 * <p>
 *     Establece la politica de autenticacion sin estado mediante jwt
 *     las reglas de acceso por URL y las politicas de CORS para la comunicacion con el frontend
 * </p>
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    /**
     * Define la cadena de filtros de seguridad HTTP de spring
     * @param http -> objeto para configurar la seguridad a nivel de peticiones web
     * @return {@link SecurityFilterChain} la cadena de filtros ensamblada
     * @throws Exception si ocurre un error al construir la configuracion
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //Desabilidato: al usar jwt en haders csrf no es necesario
                .csrf(AbstractHttpConfigurer::disable)
                //Habilita la configuracion de Cors definida en el bean corsConfigurationSource()
                .cors(Customizer.withDefaults())
                //Define la gestion de sesiones como stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Configuracion de permisos por endpoint
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                //Intercepta las peticiones con el filtro personalizado antes del filtro de autenticacion por defecto
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configuracion de las politicas de CORS
     * para permitir peticiones HTTP desde la aplicacion en React
     * @return {@link CorsConfigurationSource} con la politica de origenes y cabeceras permitiidas
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //Puertos por defecto del servidor de vite
        configuration.setAllowedOrigins(List.of("http://localhost:5174","http://localhost:5173","https://banco-web-app-frontend-n2luhp2hs-drm14.vercel.app"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
        return source;
    }
}