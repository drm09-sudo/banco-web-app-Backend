package group.bancoweb.Services;

import group.bancoweb.dtos.TransaccionResponseDTO;
import group.bancoweb.dtos.TransferenciaRequestDTO;
import group.bancoweb.entities.Transaccion;
import group.bancoweb.entities.Usuario;
import group.bancoweb.enums.TipoTransaccion;
import group.bancoweb.exceptions.RecursoNoEncontradoException;
import group.bancoweb.exceptions.ReglaNegocioException;
import group.bancoweb.exceptions.SaldoInsuficienteException;
import group.bancoweb.repositories.TransaccionRepository;
import group.bancoweb.repositories.UsuarioRepository;
 import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransaccionService implements ITransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final UsuarioRepository usuarioRepository;

    /** <p>
     * Metodo que al realiza una transaccion usando el RequestDTO y retorna un ResponseDTO con los datos de la transaccion exitosa
     * </p>
     * @param email (El email del usuario emisor para comprobar que el emisor exista)
     * @param transferenciaRequestDTO (Aqui se hace el request de la transaccion mandando el email del usuarioReceptor y la cantidad a transferir)
     * @return TransaccionResponseDTO (Retorna la respuesta de esa transaccion con datos como la id, cantidad, fecha, emailEmisor,emailReceptor y el tipo)
     */
    @Override
    public TransaccionResponseDTO realizarTransaccionPorEmail(String email, TransferenciaRequestDTO transferenciaRequestDTO) {
        //validacion 1. que el usuario emisor exista
         Usuario usuarioEmisor = usuarioRepository.findByEmail(email)
                 .orElseThrow(() -> new RecursoNoEncontradoException("El email no se encuentra registrado"));

         Usuario usuarioReceptor = usuarioRepository.findByEmail(transferenciaRequestDTO.emailReceptor())
                 .orElseThrow(() -> new RecursoNoEncontradoException("El usuario receptor no existe"));

        //Validacion 1.1, que el usuario emisor no se envie diinero a si mismo
        if(Objects.equals(usuarioEmisor.getEmail(),usuarioReceptor.getEmail())){
            throw new ReglaNegocioException("No se puede enviar saldo a si mismo");
        }
        //Validacion 1.2, que el usuario tenga suficiente saldo
        if(usuarioEmisor.getSaldo().compareTo(transferenciaRequestDTO.cantidad()) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

        //Calculo los nuevos saldos
        BigDecimal nuevoSaldoEmisor = usuarioEmisor.getSaldo().subtract(transferenciaRequestDTO.cantidad());
        BigDecimal nuevoSaldoReceptor = usuarioReceptor.getSaldo().add(transferenciaRequestDTO.cantidad());
        //Asignar nuevo saldo a los usuarios
        usuarioEmisor.setSaldo(nuevoSaldoEmisor);
        usuarioReceptor.setSaldo(nuevoSaldoReceptor);

        //Hacer transferencia
        Transaccion transaccion = Transaccion.builder()
                .cantidad(transferenciaRequestDTO.cantidad())
                .tipoTransaccion(TipoTransaccion.TRANSFERENCIA)
                .usuarioEmisor(usuarioEmisor)
                .usuarioReceptor(usuarioReceptor)
                .build();
        //Guardo la transferencia llamando al transaccionRepository
        transaccionRepository.save(transaccion);
        return new TransaccionResponseDTO(
                transaccion.getIdTransaccion(),
                transaccion.getCantidad(),
                transaccion.getFechaTransaccion(),
                usuarioEmisor.getEmail(),
                usuarioReceptor.getEmail(),
                transaccion.getTipoTransaccion(),
                "Transferencia Realizada exitosamente"
        );
    }

    /**
     * Metodo que mapea el DTO de la transaccion
     * @param transaccion (Paso como parametro la transaccion a mapear)
     * @return TransaccionResponseDTO (Con los datos esenciales de la transaccion que se quieren mapear)
     */
    @Override
    public TransaccionResponseDTO mapearDTO(Transaccion transaccion) {
        if (transaccion == null) {
            return null; //Evito errores si la transaccion llega null
        }
        //Validaciones que comprueban si el email y nombre son diferentes de null, si no lo son se va a poner por defecto N/A
        String emailReceptor = (transaccion.getUsuarioReceptor() != null)
                ? transaccion.getUsuarioReceptor().getEmail()
                : "N/A";
        String emailEmisor = (transaccion.getUsuarioEmisor() != null)
                ? transaccion.getUsuarioEmisor().getEmail()
                : "N/A";
        String nombreEmisor = (transaccion.getUsuarioEmisor()!= null)
                ? transaccion.getUsuarioEmisor().getNombre()
                : "Usuario desconocido";
        return new TransaccionResponseDTO(
                transaccion.getIdTransaccion(),
                transaccion.getCantidad(),
                transaccion.getFechaTransaccion(),
                emailEmisor,
                emailReceptor,
                transaccion.getTipoTransaccion(),
                "lista de transacciones del usuario: " +  nombreEmisor
        );
    }

    /** Metodo que obtiene el historial de transacciones pasandole el email como parametro, retornando una lista de tipo TransaccionResponseDTO
     * @param email (Parametro que se envia al metodo del repositorio para obtener el historial de transacciones asociado al email)
     * @return historialTransacciones -> (contiene una lista de obtenerHistorialPorDocumento que se encuentra en el repositorio de transaccion)
     */
    @Override
    public List<TransaccionResponseDTO> historialTransaccionesPorEmail(String email) {
        List<Transaccion> historialTransacciones =
                transaccionRepository.obtenerHistorialPorEmail(email);

        return historialTransacciones.stream()
                .map(this::mapearDTO)
                .toList();
    }
}
