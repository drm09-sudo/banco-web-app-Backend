package group.bancoweb.dtos;

/**
 * Representa la respuesta de autorizacion que revisa que haya un token al momento de hacer la autenticacion del usaurio
 * @param token -> token que se valida para comprobar que el usuario está autenticado
 */
public record AuthResponseDTO(String token) {

    public AuthResponseDTO{
        if (token == null || token.isBlank()) {
            throw new NullPointerException("El token no puede estar nulo o vacío");
        }
    }

    public String toHeaderValue(){
        return "Bearer " + token;
    }

}
