package group.bancoweb.exceptions;

/**
 * Excepcion lanzada cuando el servidor no puede procesar una solicitud enviada por un usuario
 */
public class BadRequestException extends RuntimeException{
    public BadRequestException(String mensaje){
        super(mensaje);
    }}
