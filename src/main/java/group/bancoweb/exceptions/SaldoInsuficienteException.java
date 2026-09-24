package group.bancoweb.exceptions;

/**
 * Excepcion lanzada cuando el usuario intenta realizar una transaccion pero no tiene un saldo suficiente
 */

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
