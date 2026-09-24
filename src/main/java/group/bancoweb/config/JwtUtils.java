package group.bancoweb.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utilidad de seguridad que se encarga de la generacion, extraccion de datos y
 * validacion de la autenticidad de los JSON web Tokens
 * <p>
 *     Los tokens utilizan el algoritmo HMAC-SHA y tienen una vida util de 24 horas
 * </p>
 * @author davidRecalde
 */
@Component
public class JwtUtils {

    private final String CLAVE_SECRETA;
    //24 horas en milisegundos 24h*60m*60s * 1000ms
    private final long EXPIRATION_TIME = 86400000;

    /**
     * Inyecto la clave secreta definida en el archivo application.properties
     * @param claveSecreta clave secreta configurada en {@code application.properties}
     */
    public JwtUtils(@Value("${jwt.secret}")String claveSecreta){
        this.CLAVE_SECRETA = claveSecreta;
    }
    //Metodo auxiliar que convierte la clave secreta en un codigo secretkey
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(CLAVE_SECRETA.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un nuevo JWT firmado para el usuario especificado
     * @param email -> Correo electronico que identificará al usuario del token
     * @return Cadena de texto comprimida que representa el JWT firmado
     */
    public String generarToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() +  EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Metodo que decodifica el token firmado y extrae la identidad (email) del usuario
     * @param token -> Cadena jwt proveniente del encabezado HTTP authorization
     * @return JwtException si el token está alterado o caducado
     */
    public String obtenerEmailDelToken(String token){
            Claims payload = Jwts.parser()
                .verifyWith( getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return payload.getSubject();
    }

    /**
     * Verifica la validez estructural y la firma digital del token
     * @param token -> token a validar
     * @return true si la firma es valida y no ha expirado, false si ha expirado o es invalida
     */
    public boolean validarToken(String token){
        try{
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        }catch (JwtException  | IllegalArgumentException e ){
            System.out.println("Error al procesar el token: " + e.getMessage());
            return false;
        }
    }
}
