package group.bancoweb.Services;

import group.bancoweb.dtos.TransaccionResponseDTO;
import group.bancoweb.dtos.TransferenciaRequestDTO;
import group.bancoweb.entities.Transaccion;
import group.bancoweb.entities.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITransaccionService {

    public TransaccionResponseDTO mapearDTO(Transaccion transaccion);
    @Transactional(readOnly = true)
    public List<TransaccionResponseDTO> historialTransaccionesPorEmail(String email);
    @Transactional
    public TransaccionResponseDTO realizarTransaccionPorEmail(String email,TransferenciaRequestDTO transferenciaRequestDTO);
}
