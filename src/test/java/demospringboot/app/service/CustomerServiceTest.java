package demospringboot.app.service;

import brave.*;
import brave.propagation.*;
import demospringboot.app.domain.entity.Customer;
import demospringboot.app.exception.TemplateNotFoundException;
import demospringboot.app.facade.CustomerFacade;
import demospringboot.app.web.model.*;
import demospringboot.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.*;

import java.lang.reflect.Array;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerFacade customerFacade;

    @Mock
    private Tracer tracer;

    @InjectMocks
    private CustomerService customerService;

    private long clientId;

    @BeforeAll
    static void beforeAll(){
        System.out.println("Before all tests");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After all tests");
    }

    @BeforeEach
    void setUp(){

        clientId = 1L;
        Tracer tracing = Tracing.newBuilder().build().tracer();
        TraceContext traceContext = TraceContext.newBuilder().traceId(123L).spanId(456L).build();
        Span span = tracing.nextSpan(TraceContextOrSamplingFlags.create(traceContext)).name(this.getClass().getName()).start();

        when(tracer.currentSpan()).thenReturn(span);

        System.out.println("@BeforeEach done");

    }

    @Test
    void testSave(){

        CustomerCreateModel customerCreateModel = new CustomerCreateModel(
                "Pedro Perez",
                "M",
                LocalDate.of(1990, Month.JANUARY, 1),
                "mail@mail.com.mx",
                "8181818181");

        /*when(customerFacade.save(any(Customer.class)))
                .then((Answer<Customer>) invocation ->{
                Customer customer = invocation.getArgument(0);
                customer.setId(clientId);
                return customer;
        });*/

        when(customerFacade.save(any(Customer.class)))
                .thenAnswer(invocation -> {
                    Customer customer = invocation.getArgument(0);
                    customer.setId(clientId);
                    return customer;
                });

        ResponseModel <CustomerModel> responseModel = customerService.save(customerCreateModel);

        // assert Objects.nonNull(responseModel.getData());
        assertNotNull(responseModel.getData());

        assertEquals(ResponseCode.AT_TS_001.name(), responseModel.getBusinessCode());
        assertEquals(ResponseCode.AT_TS_001.message(), responseModel.getMessage());
        assertEquals(LocalDate.now(), responseModel.getData().getCreatedAt().toLocalDate());
        assertEquals(1L, responseModel.getData().getId());

        // Verificar que durante el test se haya utilizado el metodo "exists"
        verify(customerFacade).exists(any(Customer.class));

    }

    @Test
    void testFindById(){

        Customer customer = new Customer(
                "Pedro Perez",
                Sex.M,
                LocalDate.of(1990, Month.JANUARY, 1),
                "mail@mail.com.mx",
                "8181818181");
        customer.setId(clientId);

        when(customerFacade.findCustomerById(clientId)).thenReturn(Optional.of(customer));

        ResponseModel<CustomerModel> responseModel = customerService.getCustomerById(clientId);

        // assert Objects.nonNull(responseModel.getData());
        //assertNotNull(responseModel.getData());
        assertNull(responseModel.getData());

        assertEquals(ResponseCode.AT_TS_002.name(), responseModel.getBusinessCode());
        assertEquals(ResponseCode.AT_TS_002.message(), responseModel.getMessage());
        assertEquals(customer.getName(), responseModel.getData().getName());
        assertTrue(Objects.nonNull(responseModel.getData().getPhoneNumber()));

    }

    @Test
    @Disabled
    void testFindByIdNotFound(){
        when(customerFacade.findCustomerById(clientId)).thenReturn(Optional.empty());

        assertThrows(TemplateNotFoundException.class, () -> customerService.getCustomerById(clientId));

    }

    @Test
    void testFindAll(){

        Customer customer = new Customer(
                "Pedro Perez",
                Sex.M,
                LocalDate.of(1990, Month.JANUARY, 1),
                "mail@mail.com.mx",
                "8181818181");
        customer.setId(clientId);

        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerFacade.findAllCustomers(any(Pageable.class))).thenReturn(new PageImpl<>(customers));

        ResponseModel<CustomerModel> responseModel = customerService.findAllCustomers(PageRequest.of(0,4, Sort.by("name").descending()));

        // assert Objects.nonNull(responseModel.getData());
        assertNotNull(responseModel.getData());

        assertEquals(ResponseCode.AT_TS_002.name(), responseModel.getBusinessCode());
        assertEquals(ResponseCode.AT_TS_002.message(), responseModel.getMessage());

        assertEquals(customers.size(), ((List<Customer>)responseModel.getData()).size());
        assertTrue(Objects.nonNull(responseModel.getMetadata()));

    }

}
