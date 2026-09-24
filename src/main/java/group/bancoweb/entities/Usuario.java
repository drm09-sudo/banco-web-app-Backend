package group.bancoweb.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Se detalla el modelo de la entidad usuario, mediante las especificaciones del JPA
 * donde la clase representa la tabla usuario y los atributos corresponden a los
 *  columnas mapeadas con la anotacion @Column
 */
@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class Usuario{
    @Id //llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremental
    private Integer id;
    @Column(nullable = false, unique = true)
    private String documento;
    private String nombre;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal saldo;

}
