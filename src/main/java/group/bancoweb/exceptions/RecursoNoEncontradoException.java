package group.bancoweb.exceptions;

/**
 * Excepcion lanzada si un email / id / documento no se encuentra en la BD
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String message) {
        super(message);
    }
}
