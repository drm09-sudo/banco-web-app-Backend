package group.bancoweb.controllers;


import group.bancoweb.Services.UsuarioService;
import group.bancoweb.dtos.UsuarioPerfilDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que se encarga de obtener los datos relacionados con el perfil del usuario
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    /**
     * Encargado de obtener el perfil del usuario una vez este se haya autenticado exitosamente
     * @param authentication -> Comprueba que el usuario esté autenticado previamente
     * @return {@link ResponseEntity} con mensaje de exito en caso de que el proceso sea exitoso
     */
    @GetMapping("/principal")
    public ResponseEntity<UsuarioPerfilDTO> getUsuario(Authentication authentication){
        String email = authentication.getName();
        UsuarioPerfilDTO usuarioEncontrado = usuarioService.obtenerPerfilPorEmail(email);
        return ResponseEntity.ok(usuarioEncontrado);
    }
}
