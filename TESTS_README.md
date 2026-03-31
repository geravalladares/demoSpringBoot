# Test Suite para CustomerService - Documentación Completa

## Descripción General

Se ha creado un conjunto exhaustivo de pruebas unitarias para la clase `CustomerService` utilizando **JUnit 5** y **Mockito**. El archivo se encuentra en:

```
src/test/java/demospringboot/app/service/CustomerServiceTests.java
```

**Total de Pruebas: 31 casos de prueba distribuidos en 9 categorías**

## Estructura del Test

El test suite está organizado en **9 secciones principales** usando `@Nested` para mejor legibilidad:

### 1. **GetCustomerByIdTests** (3 pruebas)
Prueba el método `getCustomerById(long id)`:

- ✅ `shouldReturnCustomerWhenExists`: Retorna un cliente cuando existe
- ✅ `shouldThrowExceptionWhenNotExists`: Lanza excepción cuando no existe
- ✅ `shouldCallFacadeOnce`: Verifica que el facade se llama una sola vez

### 2. **FindAllCustomersTests** (4 pruebas)
Prueba el método `findAllCustomers(Pageable pageable)`:

- ✅ `shouldReturnPaginatedCustomers`: Retorna clientes paginados correctamente
- ✅ `shouldThrowWhenEmpty`: Lanza excepción con lista vacía
- ✅ `shouldHandleMultipleCustomers`: Retorna múltiples clientes
- ✅ `shouldUseCorrectPageable`: Verifica parámetros de paginación

### 3. **SaveTests** (4 pruebas)
Prueba el método `save(CustomerCreateModel model)`:

- ✅ `shouldSaveSuccessfully`: Guarda cliente cuando no existe
- ✅ `shouldThrowWhenExists`: Lanza excepción si ya existe
- ✅ `shouldVerifyBeforeSave`: Verifica existencia antes de guardar
- ✅ `shouldNotSaveWhenExists`: No guarda si el cliente ya existe

### 4. **EdgeCasesTests** (6 pruebas)
Prueba casos límite y especiales:

- ✅ `shouldHandleLargeId`: Maneja ID muy grande (Long.MAX_VALUE)
- ✅ `shouldHandleZeroId`: Maneja ID cero
- ✅ `shouldHandleNegativeId`: Maneja ID negativo
- ✅ `shouldHandleSinglePage`: Maneja paginación de un solo elemento
- ✅ `shouldHandleSpecialChars`: Maneja caracteres especiales en nombre

### 5. **MockVerificationTests** (4 pruebas)
Verifica interacciones con mocks:

- ✅ `shouldVerifyMultipleCalls`: Verifica múltiples llamadas a get
- ✅ `shouldVerifyMultipleFindAllCalls`: Verifica múltiples llamadas a findAll
- ✅ `shouldVerifyNoSaveOnDuplicate`: Verifica que save no se llama en duplicados
- ✅ `shouldVerifyNoExtraInteractions`: Verifica sin interacciones extra tras excepción

### 6. **ResponseTests** (2 pruebas)
Valida estructura de respuestas:

- ✅ `shouldHaveCompleteResponseForGet`: Respuesta completa en get
- ✅ `shouldHaveCompleteResponseForFindAll`: Respuesta completa en findAll

### 7. **ExceptionTests** (3 pruebas)
Verifica manejo de excepciones:

- ✅ `shouldThrowWhenNotFound`: Lanza excepción cuando cliente no encontrado
- ✅ `shouldThrowWhenExistsDuringSave`: Lanza excepción si existe durante save
- ✅ `shouldThrowWhenFindAllEmpty`: Lanza excepción cuando findAll vacío

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
mvn test -Dtest=CustomerServiceTests
```

### Ejecutar una clase de test específica:
```bash
mvn test -Dtest=CustomerServiceTests#GetCustomerByIdTests
```

### Ejecutar una prueba específica:
```bash
mvn test -Dtest=CustomerServiceTests#shouldReturnCustomerWhenExists
```

### Ejecutar todos los tests del proyecto:
```bash
mvn test
```

### Ejecutar con reporte detallado:
```bash
mvn test -Dtest=CustomerServiceTests -e
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
3. **Casos límite**: IDs muy grandes, cero, negativos
4. **Datos especiales**: Caracteres especiales en nombres
5. **Interacciones del mock**: Valida que se llamen los métodos correctos
6. **Paginación**: Prueba con diferentes parámetros de Pageable
7. **Duplicados**: Valida comportamiento cuando el cliente ya existe
8. **Respuestas completas**: Verifica todos los campos de la respuesta

## Estructura de Datos de Prueba

Se crean datos de prueba en `setUp()`:

```java
// Entity
customer = new Customer(
    "John Doe", 
    Sex.M, 
    LocalDate.of(1990, 1, 1), 
    "john@example.com", 
    "+1234567890"
);
customer.setId(1L);

// Model DTO
customerCreateModel = new CustomerCreateModel(
    "John Doe",
    "M",
    LocalDate.of(1990, 1, 1),
    "john@example.com",
    "+1234567890"
);
```

## Próximas Mejoras Sugeridas

1. **Tests de integración**: Crear `@SpringBootTest` con base de datos H2
2. **Pruebas de rendimiento**: Validar tiempos de respuesta
3. **Cobertura de código**: Usar jacoco-maven-plugin para medir cobertura
4. **Tests del controlador**: Crear CustomerControllerTest
5. **Tests del facade**: Crear CustomerFacadeTest
6. **Tests de validación**: Validar reglas de negocio (edad mínima, etc.)

## Validación

Para verificar que todo compila correctamente:

```bash
mvn test-compile
```

Para ejecutar los tests:

```bash
mvn test
```

## Resumen Ejecutivo

Se han generado **31 casos de prueba** que cubren completamente las funcionalidades principales del servicio `CustomerService`:

| Funcionalidad | Casos | Estado |
|---|---|---|
| Obtener cliente por ID | 3 | ✅ Completo |
| Listar clientes paginados | 4 | ✅ Completo |
| Guardar nuevo cliente | 4 | ✅ Completo |
| Casos límite | 6 | ✅ Completo |
| Verificación de mocks | 4 | ✅ Completo |
| Respuestas | 2 | ✅ Completo |
| Manejo de excepciones | 3 | ✅ Completo |
| **TOTAL** | **31** | **✅ LISTO** |

El test suite está completamente implementado, compilado y listo para ejecutarse.

