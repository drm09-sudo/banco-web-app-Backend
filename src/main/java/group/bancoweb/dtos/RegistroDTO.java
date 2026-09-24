package group.bancoweb.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Repreenta los datos requeridos al momento de registrar un usuario nuevo con sus respectivas anotaciones de validación
 * @param documento -> documento de identidad del usuario
 * @param nombre -> nombre completo del usuario
 * @param email -> correo electronico del usuario
 * @param password -> Contraseña creada por el usuario
 * @param saldo -> saldo inicial que el usuario ingresa al momento de registrarse
 */
//Utilizo la clase record para usar el patron DTO y transportar datos
//Añado las anotaciones correspondientes a cada campo para realizar validaciones previo al registro
public record RegistroDTO(
        @NotBlank(message = "El campo documento es obligatorio")
        @Pattern(regexp = "^[0-9]+$", message = "Este campo solo puede contener números")
        String documento,
        @NotBlank(message = "El campo nombre es obligatorio")
        String nombre,
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El formato del email debe ser valido")
        String email,
        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, max = 15, message = "La contraseña debe tener al menos 8 caracteres")
        String password,
        @NotNull(message = "El saldo no puede estar vacío")
        @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
        BigDecimal saldo) {

}
