package group.bancoweb.dtos;

import group.bancoweb.enums.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa la respuesta a una transaccion hecha con datos relevantes para el historial
 * @param idTransaccion -> Id generada para la transaccion hecha
 * @param cantidad -> Cantidad de dinero transferida
 * @param fecha -> fecha del momento en que se hizo la transaccion
 * @param emailEmisor -> correo electronico de el usuario emisor que hizo la transaccion
 * @param emailReceptor -> correo electronico de la persona que recibió el dinero
 * @param tipoTransaccion -> el tipo de transaccion que se hizo, ej. Transacción, giro, deposito (Por el momento solo transaccion)
 * @param mensaje -> mensaje que indica que la transaccion fue exitosa
 */
public record TransaccionResponseDTO(
        Long idTransaccion,
        BigDecimal cantidad,
        LocalDateTime fecha,
        String emailEmisor,
        String emailReceptor,
        TipoTransaccion tipoTransaccion,
        String mensaje

)
{

}
