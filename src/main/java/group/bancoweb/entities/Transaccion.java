package group.bancoweb.entities;


import group.bancoweb.enums.TipoTransaccion;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Se detalla el modelado de la entidad transaccion
 * donde la clase corresponde a la tabla y los atributos corresponden
 * a las columnas mapeadas mediante la anotacion @Column
 */
@Entity
@Table(name = "transaccion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;
    @Column(nullable = false)
    private BigDecimal cantidad;
    private LocalDateTime fechaTransaccion;
    @Enumerated(EnumType.STRING) //para que se vea como texto en la bd el enum
    private TipoTransaccion tipoTransaccion;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_emisor_id", nullable = false)
    private Usuario usuarioEmisor;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_receptor_id")
    private Usuario usuarioReceptor;


    @PrePersist
    public void obtenerFechaYHora() {
        this.fechaTransaccion = LocalDateTime.now();
    }
}


