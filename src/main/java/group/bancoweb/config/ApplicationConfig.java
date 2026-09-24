package group.bancoweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuracion de componentes y beans globales de la configuracion
 */
@Configuration
public class ApplicationConfig {
    /**
     * Defino el algoritmo de encriptacion de passwords
     * utulizo el registro y autenticacion de usuario
     *
     * @return instancia de {@link PasswordEncoder} basada en bcrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
