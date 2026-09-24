package group.bancoweb.dtos;

import java.time.LocalDateTime;

/**
 * Representa los datos que se muestran como respuesta al momento de que ocurra un error
 * @param timestamp -> es la marca de tiempo al momomento de que el error ocurra
 * @param status -> El codigo de estado del error o valor
 * @param error ->  La razón del error
 * @param message -> Mensaje proporcionado por el programador para
 * @param path -> la ruta
 */
public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) { }
