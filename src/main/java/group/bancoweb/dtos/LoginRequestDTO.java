package group.bancoweb.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** Representa los datos que el usuario registrado debe ingresar para poder hacer login
 *
 * @param email -> el correo electronico del usuario previamente registrado
 * @param password -> la contraseña que el usuario creó al momento de registrarse
 */
public record LoginRequestDTO(

        @NotBlank
        @Email
        String email,
        @NotBlank
        String password) {
}
