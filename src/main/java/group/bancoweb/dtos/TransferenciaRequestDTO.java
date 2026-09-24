package group.bancoweb.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Representa los datos requeridos para hacer un request de una transferencia
 * @param emailReceptor -> el correo electronico del usuario que va a recibir la cantidad a transferir
 * @param cantidad -> la cantidad "dinero" que va a recibir el usuario receptor
 */
public record TransferenciaRequestDTO(
        @NotBlank(message = "El email del receptor no puede estar vacío")
        @Email(message = "Debe ingresar un formato de email valido")
        String emailReceptor,
        @NotNull(message = "La cantidad no puede estar vacía.")
        @Positive(message = "El monto a transferir debe ser mayor a cero")
        BigDecimal cantidad
) {}
