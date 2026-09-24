package group.bancoweb.Services;

import group.bancoweb.dtos.UsuarioPerfilDTO;
import group.bancoweb.entities.Usuario;
import group.bancoweb.exceptions.RecursoNoEncontradoException;
import group.bancoweb.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Metodo obtiene el perfil de un usuario utilizando el email como parametro de busqueda
     * <p>
     *     Valida si existe un usuario registrado con ese email
     *     Si no lo encuentra lanza la excepcion de que no se encuentra el usuario.
     *     Si lo encuentra retorna los datos del usuario mediante el UsuarioPerfilDTO
     * </p>
     * @param email
     * @return UsuarioPerfilDTO (Con los datos del usuario: documento, nombre, email, saldo)
     */
    @Override
    public UsuarioPerfilDTO obtenerPerfilPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado."));
        return new UsuarioPerfilDTO( //Retorno el perfil del usuario con los datos del DTO
                usuario.getDocumento(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getSaldo()
        );
    }
}
