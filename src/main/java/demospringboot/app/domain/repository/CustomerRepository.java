package demospringboot.app.domain.repository;

import demospringboot.app.domain.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * CustomerRepository is an interface that defines methods for accessing and managing customer data.
 * It provides methods to retrieve all customers with pagination and to find a customer by their ID.
 */

//TODO: Implementar CustomerRepository usando Spring Data JPA o cualquier otra tecnología de persistencia que prefieras.

//TODO: Las consultas que regresan un objeto envolverlas en Optional para manejar mejor los casos de datos no encontrados y evitar NullPointerExceptions.
//TODO: Las consultas que regresan una lista de objetos envolverlas en Page para manejar mejor la paginación y evitar problemas de rendimiento al cargar grandes cantidades de datos.

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Crear método para encontrar un cliente por Nombre, fecha de nacimiento y correo electrónico.
    Optional<Customer> findByNameAndBirthDateAndEmail(String name, LocalDate birthDate, String email);
}
