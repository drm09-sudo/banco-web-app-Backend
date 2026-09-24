package group.bancoweb.controllers;

import group.bancoweb.Services.TransaccionService;
import group.bancoweb.dtos.TransaccionResponseDTO;
import group.bancoweb.dtos.TransferenciaRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador rest que se encarga de gestionar las operaciones principales de el sistema bancario web
 * (Realizar transferencias, obtener el historial de transferencias)
 */
@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;


    /**
     * Realiza una transaccion cuando comprueba que el usuario está autenticado
     * @param authentication -> comprueba que el usuario esté autenticado actualmente
     * @param transferenciaRequestDTO -> hace referencia a los datos que se van a solicitar para realizar una transferencia, correoReceptor,cantidad
     * @return {@link ResponseEntity} con mensaje de exito en caso de que el proceso sea exitoso
     */
    @PostMapping("/transferir")
    public ResponseEntity<TransaccionResponseDTO> realizarTransaccion(Authentication authentication,@Valid @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
        String email = authentication.getName();
        TransaccionResponseDTO transaccionResponseDTO = transaccionService.realizarTransaccionPorEmail(email, transferenciaRequestDTO );
        return ResponseEntity.ok(transaccionResponseDTO);
    }

    /**
     * Consulta el historial de transacciones completo del usuario asociado a la cuenta
     * @param authentication -> Comprueba que el usuario esté previamente autenticado
     * @return {@link ResponseEntity} con mensaje de exito en caso de que el proceso sea exitoso
     */
    @GetMapping("/historial")
    public ResponseEntity<List<TransaccionResponseDTO>> obtenerHistorialPorDocumento(Authentication authentication ) {
        String email = authentication.getName();
        List<TransaccionResponseDTO> transferenciaResponseDTO;
        transferenciaResponseDTO = transaccionService.historialTransaccionesPorEmail(email);
        return ResponseEntity.ok(transferenciaResponseDTO);
    }

}
