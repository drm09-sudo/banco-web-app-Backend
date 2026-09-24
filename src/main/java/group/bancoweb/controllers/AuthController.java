package group.bancoweb.controllers;

import group.bancoweb.Services.IAuthService;
import group.bancoweb.dtos.AuthResponseDTO;
import group.bancoweb.dtos.LoginRequestDTO;
import group.bancoweb.dtos.RegistroDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que se encarga de la autenticacion y el registro de nuevos usuarios
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService iAuthService;

    /**
     * Encargado de registrar a un usuario en la DB para despues pueda hacer inicio de sesión
     * @param dto -> Objeto JSON que tiene los datos necesarios para hacer el registro ({@link RegistroDTO})
     * @return {@link ResponseEntity} con un mensaje de exito si el proceso fue exitoso
     */
    @PostMapping("/registro")
    public ResponseEntity<AuthResponseDTO> registrarUsuario(@Valid @RequestBody RegistroDTO dto) {
        //1. Llamo al servicio pasandole el dto
        AuthResponseDTO authResponseDTO = iAuthService.registro(dto);
        //2. Retorno la respuesta con el estado 201 CREATED
        return  ResponseEntity.status(HttpStatus.CREATED).body(authResponseDTO);
    }

    /**
     * Procesa los datos ingresados por el usuario para hacer el inicio de sesión
     * @param dto -> objeto JSON del cuerpo de la peticion mediante ({@link LoginRequestDTO})
     * @return {@link ResponseEntity} con un mensaje de exito si el proceso fue exitoso
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> iniciarSesion(@Valid @RequestBody LoginRequestDTO dto) {
        //Capuro la respuesta de mi servicio en una variable de tipo LRDTO
        AuthResponseDTO authResponseDTO = iAuthService.login(dto);
        //Paso la variable como retorno
        return ResponseEntity.ok(authResponseDTO);
    }




}
