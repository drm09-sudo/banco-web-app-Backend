package group.bancoweb.repositories;

import group.bancoweb.entities.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    @Query("SELECT t FROM Transaccion t " +
    "LEFT JOIN FETCH t.usuarioEmisor " +
    "LEFT JOIN FETCH t.usuarioReceptor " +
            "WHERE t.usuarioEmisor.email =:email OR t.usuarioReceptor.email =:email ")
            List<Transaccion> obtenerHistorialPorEmail(@Param("email") String emailUsuario);

    //public List<Transaccion> obtenerHistorialPorEmail(String email);
}
