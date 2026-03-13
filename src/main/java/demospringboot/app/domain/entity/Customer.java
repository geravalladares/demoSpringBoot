package demospringboot.app.domain.entity;

import demospringboot.remoto.dto.UserDto;
import demospringboot.util.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Represents a customer entity in the application.
 * This class is mapped to the "clientes" table in the "clientes" schema of the database.
 * It includes fields for customer information
 */



// TODO: Quitar anotacion Getter y Setter a nivel clase
// TODO: Anotar con Setter solo los campos que se espera que puedan ser modificados después de la creación del cliente
// TODO: Anotar con Getter solo los campos que se espera que sean de solo lectura después de la creación del cliente
// TODO: Usar patron @Builder de Lombok cuando se requiera un constructor con mas de 7 parametros para mejorar la legibilidad y mantenibilidad del código


@Entity
@Table(name = "clientes", schema = "clientes",
        indexes =   {@Index(name = "cliente_id_idx", columnList = "id"),
                    @Index(name = "cliente_nombre_idx", columnList = "nombre"),
                    @Index(name = "cliente_fecha_nacimiento_idx", columnList = "fecha_nacimiento"),
                    @Index(name = "cliente_correo_idx", columnList = "correo")})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "nombre", nullable = false)
    @Getter
    private String name;

    @Column(name = "sexo", nullable = false)
    @Getter
    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    @Column(name = "fecha_nacimiento", nullable = false)
    @Getter
    private LocalDate birthDate;

    @Column(name = "correo", nullable = false)
    @Getter
    private String email;

    @Column(name = "telefono", nullable = false)
    @Getter
    private String phoneNumber;

    @Column(name = "fecha_alta", nullable = false)
    @Getter
    private Long createdAt;

    @Transient
    @Getter
    private UserDto user;

    protected Customer() {}

    // TODO: Validations for empty data and adult age should be implemented in the service layer or using Bean Validation (e.g., @NotEmpty, @Past, etc.)
    public  Customer(String name, Sex sex, LocalDate birthDate, String email, String phoneNumber) {
        this.name = name;   //isEmptyData
        this.sex = sex;
        this.birthDate = birthDate; // isAdultAge
        this.email = email; //isEmptyData
        this.phoneNumber = phoneNumber; //isEmptyData
        this.createdAt = Instant.now().toEpochMilli();
    }

    // TODO: Implement masking for email and phone number in the toString method for better security when logging or displaying customer information.
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +    //maskEmail(email) +
                ", phoneNumber='" + phoneNumber + '\'' +    //maskPhoneNumber(phoneNumber)
                ", createdAt=" + createdAt +
                '}';
    }
}
