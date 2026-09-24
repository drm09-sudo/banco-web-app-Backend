package group.bancoweb.dtos;

import java.math.BigDecimal;

/**
 * Representa los datos requeridos para crear el perfil de usuario
 * @param documento -> Documento de identidad del usuario
 * @param nombre -> Nombre completo del usuario
 * @param email -> Correo electronico del usuario
 * @param saldo -> Cantidad actual de "dinero" que el usuario posee en su cuenta
 */
public record UsuarioPerfilDTO(String documento,
                               String nombre, String email, BigDecimal saldo) {}
