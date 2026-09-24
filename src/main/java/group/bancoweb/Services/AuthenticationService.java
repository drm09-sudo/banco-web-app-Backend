package group.bancoweb.Services;

import group.bancoweb.config.JwtUtils;
import group.bancoweb.dtos.AuthResponseDTO;
import group.bancoweb.dtos.LoginRequestDTO;
import group.bancoweb.dtos.RegistroDTO;
import group.bancoweb.entities.Usuario;
import group.bancoweb.exceptions.BadRequestException;
import group.bancoweb.exceptions.CredencialesErroneasExcepcion;
import group.bancoweb.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthService{

    //Inyeccion de usuarioRepository para consultar/ guardar en la db
    private final UsuarioRepository usuarioRepository;
    // para cifrar las contraseñas y compararlas
    private final PasswordEncoder passwordEncoder;
    // para emitir el token
    private final JwtUtils jwtUtils;

    /** Metodo que permite al usuario registrado iniciar sesion y genera el token de autenticacion
     * @param dto (el LoginRequestDTO contiene el email y la contraseña del usuario que va a iniciar sesion)
     * @return AuthResponseDTO( se devuelve la respuesta y se verifica si el token está o no vacio)
     */
    @Override
    public AuthResponseDTO login(LoginRequestDTO dto) {
        //verifico si el usuario existe en la db
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(()-> new CredencialesErroneasExcepcion("Credenciales Invalidas"));
        //Si la password no coincide, lanzar excepcion
        if (!passwordEncoder.matches(dto.password(), usuario.getPassword()))
            throw new CredencialesErroneasExcepcion("Credenciales Invalidas");
        //Si la pass coincide, guardar el token
        var usuarioToken = jwtUtils.generarToken(usuario.getEmail());
        return new AuthResponseDTO(usuarioToken);
    }

    /** Metodo que permite a un usuario nuevo registrarse
     * realiza validaciones en el documento y el email antes de permitir al registrar un usuario nuevo
     *
     * @param dto (Dto con los datos del usuario que deben ser ingresados para hacer el registro)
     * @return AuthResponseDTO( retorna una respuesta de autorizacion donde retorna el token generado para autenticación)
     */
    @Override
    public AuthResponseDTO registro(RegistroDTO dto) {
        //verifico que el email no exista para poder registrarlo
         if(usuarioRepository.existsByEmail(dto.email()))
         {
             throw new BadRequestException("El email ya existe");
         }
         if(usuarioRepository.existsByDocumento(dto.documento()))
         {
             throw new BadRequestException("El documento ya existe");
         }
        //Creo el usuario
        Usuario usuario = new Usuario();
                usuario.setDocumento(dto.documento());
                usuario.setNombre(dto.nombre());
                usuario.setEmail(dto.email());
                usuario.setPassword(passwordEncoder.encode(dto.password()));
                usuario.setSaldo(dto.saldo());
                //Llamo al repositorio para guardar al usuario
                usuarioRepository.save(usuario);
                //Genero el token con el email del usuario registrado
                var usuarioToken = jwtUtils.generarToken(usuario.getEmail());

        return new AuthResponseDTO(usuarioToken); //retorno el token generado
    }
}
