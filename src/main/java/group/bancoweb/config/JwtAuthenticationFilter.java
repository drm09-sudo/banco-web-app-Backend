package group.bancoweb.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro de seguridad que interepta cada peticion HTTP ({@link OncePerRequestFilter})
 * para autenticar al usuario mediante tokens jwt
 * <p>
 *     Extrae el token de la cabecera {@code Authorization}, valida su firma mediante
 *      {@link JwtUtils} y establece el contexto de autenticacion en spring security
 * </p>
 */
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //inyecto JwtUtils
    private final JwtUtils jwtUtils;


    /**
     * Define las condiciones para omitir la ejecucion de este filtro.
     * <p>
     *     Se ignoran las peticiones {@code OPTIONS} (CORS- pre flight) y los endpoints
     *     publicos de autenticacion para optimizar el rendimiento y evitar bloqueos
     * </p>
     * @param request current HTTP request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return "OPTIONS".equalsIgnoreCase(request.getMethod()) || path.contains("/auth/");
    }

    /**
     * Procesa la peticion entrante, verifica el token beare y autentica al usuario
     * en el {@link SecurityContextHolder}
     * @param request -> Peticion HTTP entrante.
     * @param response -> Respuesta HTTP en construccion
     * @param filterChain -> Cadena de filtros de spring security a continuar.
     * @throws ServletException -> si ocurre un error en el servlet
     * @throws IOException -> si ocurre un error de lectura o escritura de datos
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //Extraigo el encabezado de autenticacion
        String header = request.getHeader("Authorization");
        // Verifico el formato estandar: "Bearer <token>"
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7).trim(); //aislo el token quitando el bearer
            boolean esValidoElToken = jwtUtils.validarToken(token);

            if (esValidoElToken) {
                String email = jwtUtils.obtenerEmailDelToken(token);

                if (email !=null){
                    //Creo el objeto de autenticacion en memoria para el usuario actual
                    var authToken = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                    //Vinculo detalles adicionales de la peticion HTTP, ej. la ip del cliente
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    //Registro el usuario como autenticado para esta peticion
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        //paso el control al siguiente filtro en la cadena de spring security
        filterChain.doFilter(request,response);
    }
}
