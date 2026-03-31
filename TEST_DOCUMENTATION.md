# Test Suite para CustomerService - Documentación

## Descripción General

Se ha creado un conjunto exhaustivo de pruebas unitarias para la clase `CustomerService` utilizando **JUnit 5** y **Mockito**. El archivo se encuentra en:

```
src/test/java/demospringboot/app/service/CustomerServiceTests.java
```

## Estructura del Test

El test suite está organizado en **9 secciones principales** usando `@Nested` para mejor legibilidad y organizació

### 1. **GetCustomerByIdTests** (4 pruebas)
Prueba el método `getCustomerById(long id)`:

- ✅ `shouldReturnCustomerWhenExists`: Verifica que retorna un cliente cuando existe
- ✅ `shouldThrowExceptionWhenCustomerNotExists`: Verifica que lanza excepción cuando no existe
- ✅ `shouldThrowExceptionWithCorrectCode`: Valida el código de respuesta correcto (AT_TS_003)
- ✅ `shouldCallFacadeExactlyOnce`: Verifica que el facade se llama una sola vez

### 2. **FindAllCustomersTests** (5 pruebas)
Prueba el método `findAllCustomers(Pageable pageable)`:

- ✅ `shouldReturnPaginatedCustomersWhenExists`: Retorna clientes paginados
- ✅ `shouldThrowExceptionWhenNoCustomersExist`: Lanza excepción con lista vacía
- ✅ `shouldThrowExceptionWithCorrectCodeForEmptyList`: Valida código AT_TS_003
- ✅ `shouldReturnMultipleCustomersInResponse`: Retorna múltiples clientes
- ✅ `shouldUseCorrectPageableParameters`: Verifica parámetros de paginación

### 3. **SaveTests** (6 pruebas)
Prueba el método `save(CustomerCreateModel model)`:

- ✅ `shouldSaveCustomerSuccessfully`: Guarda cliente cuando no existe
- ✅ `shouldThrowExceptionWhenCustomerAlreadyExists`: Lanza excepción si ya existe
- ✅ `shouldThrowExceptionWithCorrectCodeForDuplicate`: Valida código AT_TS_004
- ✅ `shouldVerifyCustomerExistenceBeforeSaving`: Verifica existencia antes de guardar
- ✅ `shouldNotSaveWhenCustomerExists`: No guarda si el cliente ya existe
- ✅ `shouldReturnResponseWithCorrectCodeWhenSavingSuccessfully`: Retorna código AT_TS_001

### 4. **IntegrationTests** (3 pruebas)
Prueba escenarios de integración:

- ✅ `shouldHandleMultipleOperationsInSequence`: Ejecuta múltiples operaciones
- ✅ `shouldHandleExceptionDuringGetOperation`: Maneja excepciones en get
- ✅ `shouldHandleExceptionDuringSaveOperation`: Maneja excepciones en save

## Total de Pruebas: 18 casos de prueba

## Dependencias Utilizadas

Las siguientes dependencias ya están en `pom.xml`:

```xml
<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Mockito viene incluido en spring-boot-starter-test -->
```

## Cómo Ejecutar los Tests

### Ejecutar todos los tests de CustomerService:
```bash
mvn test -Dtest=CustomerServiceTest
```

### Ejecutar una clase de test específica (ej. GetCustomerByIdTests):
```bash
mvn test -Dtest=CustomerServiceTest#GetCustomerByIdTests
```

### Ejecutar una prueba específica:
```bash
mvn test -Dtest=CustomerServiceTest#shouldReturnCustomerWhenExists
```

### Ejecutar todos los tests del proyecto:
```bash
mvn test
```

### Ejecutar con reporte detallado:
```bash
mvn test -Dtest=CustomerServiceTest -e
```

## Características del Test Suite

✨ **Uso de Mocks:**
- `@Mock CustomerFacade`: Mockea las llamadas al facade
- `@Mock Tracer`: Mockea el servicio de trazabilidad
- `@Mock Span`: Mockea la entidad Span del tracer
- `@InjectMocks CustomerService`: Inyecta los mocks en el servicio

✨ **Verificaciones con Mockito:**
- `verify()`: Verifica que los métodos se llamaron correctamente
- `when()...thenReturn()`: Configura el comportamiento esperado
- `times()`, `never()`: Valida el número de invocaciones

✨ **Assertions con JUnit 5:**
- `assertNotNull()`: Verifica que el objeto no es nulo
- `assertEquals()`: Compara valores esperados con actuales
- `assertThrows()`: Verifica que se lanza la excepción correcta

✨ **Organización:**
- `@Nested`: Agrupa pruebas relacionadas
- `@DisplayName`: Nombres descriptivos en español
- `@BeforeEach`: Configuración inicial para cada prueba

## Cobertura de Casos

El test suite cubre:

1. **Casos exitosos**: Cuando las operaciones se realizan correctamente
2. **Casos de excepción**: Cuando ocurren errores esperados
3. **Validaciones de código de respuesta**: Verifica ResponseCode correcto
4. **Interacciones del mock**: Valida que se llamen los métodos correctos
5. **Paginación**: Prueba con diferentes parámetros de Pageable
6. **Duplicados**: Valida comportamiento cuando el cliente ya existe

## Estructura de Datos de Prueba

Se crean datos de prueba en `setUp()`:

```java
// Entity
customer.setId(1L);
customer.setName("John Doe");
customer.setSex(Sex.M);
customer.setBirthDate(LocalDate.of(1990, 1, 1));
customer.setEmail("john@example.com");
customer.setPhoneNumber("+1234567890");

// Model DTO
customerCreateModel.setName("John Doe");
customerCreateModel.setSex(Sex.M);
customerCreateModel.setBirthDate(LocalDate.of(1990, 1, 1));
customerCreateModel.setEmail("john@example.com");
customerCreateModel.setPhoneNumber("+1234567890");
```

## Próximas Mejoras Sugeridas

1. **Tests de integración**: Crear `@SpringBootTest` con base de datos H2
2. **Pruebas de rendimiento**: Validar tiempos de respuesta
3. **Cobertura de código**: Usar jacoco-maven-plugin para medir cobertura
4. **Tests de controlador**: Crear CustomerControllerTest
5. **Tests del facade**: Crear CustomerFacadeTest

## Validación

Para verificar que todo compila correctamente:

```bash
mvn test-compile
```

El test suite está listo para ser ejecutado y proporciona una cobertura completa del servicio `CustomerService`.


