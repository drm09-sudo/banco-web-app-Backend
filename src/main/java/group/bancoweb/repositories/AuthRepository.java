package group.bancoweb.repositories;

import group.bancoweb.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Usuario,Long> {
    //para encontrar un usuario por email
    //Optional para evitar nullpointExceptions al declarar que debe ser de tipo usuario
    Optional<Usuario> findByEmail(String email);
}
