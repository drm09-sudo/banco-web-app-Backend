package group.bancoweb.exceptions;

/**
 * Excepcion lanzada cuando un usuario incumple una regla establecida
 * Ej. Intentar mandarse saldo a si mismo
 */
public class ReglaNegocioException extends RuntimeException{
    public ReglaNegocioException(String message) {
        super(message);
    }
}
