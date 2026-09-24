package group.bancoweb.exceptions;

/**
 * Excepcion lanzada cuando el usuario ingresa datos erroneos al momento de iniciar sesión
 */
public class CredencialesErroneasExcepcion extends RuntimeException {
    public CredencialesErroneasExcepcion(String mensaje) {
        super(mensaje);
    }
}
