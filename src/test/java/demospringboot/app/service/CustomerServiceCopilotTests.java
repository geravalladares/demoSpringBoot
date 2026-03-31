package demospringboot.app.service;

import brave.Span;
import brave.Tracer;
import demospringboot.app.domain.entity.Customer;
import demospringboot.app.exception.TemplateNotFoundException;
import demospringboot.app.facade.CustomerFacade;
import demospringboot.app.web.model.CustomerCreateModel;
import demospringboot.app.web.model.CustomerModel;
import demospringboot.app.web.model.ResponseModel;
import demospringboot.util.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService Test Suite")
class CustomerServiceCopilotTest {

    @Mock
    private CustomerFacade customerFacade;

    @Mock
    private Tracer tracer;

    @Mock
    private Span span;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerCreateModel customerCreateModel;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);

        customer = new Customer("John Doe", Sex.M, LocalDate.of(1990, 1, 1), "john@example.com", "+1234567890");
        customer.setId(1L);

        customerCreateModel = new CustomerCreateModel(
            "John Doe",
            "M",
            LocalDate.of(1990, 1, 1),
            "john@example.com",
            "+1234567890"
        );
    }

    @Nested
    @DisplayName("getCustomerById Tests")
    class GetCustomerByIdTests {

        @Test
        @DisplayName("Should return customer when customer exists")
        void shouldReturnCustomerWhenExists() {
            when(customerFacade.findCustomerById(1L)).thenReturn(Optional.of(customer));
            ResponseModel<CustomerModel> response = customerService.getCustomerById(1L);
            assertNotNull(response);
            assertNotNull(response.getBusinessCode());
            assertNotNull(response.getData());
            verify(customerFacade, times(1)).findCustomerById(1L);
        }

        @Test
        @DisplayName("Should throw exception when customer does not exist")
        void shouldThrowExceptionWhenNotExists() {
            when(customerFacade.findCustomerById(99L)).thenReturn(Optional.empty());
            assertThrows(TemplateNotFoundException.class, () -> customerService.getCustomerById(99L));
            verify(customerFacade, times(1)).findCustomerById(99L);
        }

        @Test
        @DisplayName("Should call facade exactly once")
        void shouldCallFacadeOnce() {
            when(customerFacade.findCustomerById(1L)).thenReturn(Optional.of(customer));
            customerService.getCustomerById(1L);
            verify(customerFacade, times(1)).findCustomerById(1L);
            verifyNoMoreInteractions(customerFacade);
        }
    }

    @Nested
    @DisplayName("findAllCustomers Tests")
    class FindAllCustomersTests {

        @Test
        @DisplayName("Should return paginated customers")
        void shouldReturnPaginatedCustomers() {
            Page<Customer> page = new PageImpl<>(List.of(customer), pageable, 1);
            when(customerFacade.findAllCustomers(pageable)).thenReturn(page);
            ResponseModel<CustomerModel> response = customerService.findAllCustomers(pageable);
            assertNotNull(response);
            assertNotNull(response.getBusinessCode());
            assertNotNull(response.getData());
            verify(customerFacade, times(1)).findAllCustomers(pageable);
        }

        @Test
        @DisplayName("Should throw exception when no customers exist")
        void shouldThrowWhenEmpty() {
            Page<Customer> emptyPage = new PageImpl<>(List.of(), pageable, 0);
            when(customerFacade.findAllCustomers(pageable)).thenReturn(emptyPage);
            assertThrows(TemplateNotFoundException.class, () -> customerService.findAllCustomers(pageable));
        }

        @Test
        @DisplayName("Should handle multiple customers")
        void shouldHandleMultipleCustomers() {
            Customer customer2 = new Customer("Jane Doe", Sex.F, LocalDate.of(1992, 5, 15), "jane@example.com", "+0987654321");
            customer2.setId(2L);
            Page<Customer> page = new PageImpl<>(List.of(customer, customer2), pageable, 2);
            when(customerFacade.findAllCustomers(pageable)).thenReturn(page);
            ResponseModel<CustomerModel> response = customerService.findAllCustomers(pageable);
            assertNotNull(response);
            verify(customerFacade, times(1)).findAllCustomers(pageable);
        }

        @Test
        @DisplayName("Should use correct pageable parameters")
        void shouldUseCorrectPageable() {
            Pageable customPageable = PageRequest.of(2, 20);
            Page<Customer> page = new PageImpl<>(List.of(customer), customPageable, 1);
            when(customerFacade.findAllCustomers(customPageable)).thenReturn(page);
            customerService.findAllCustomers(customPageable);
            verify(customerFacade, times(1)).findAllCustomers(customPageable);
        }
    }

    @Nested
    @DisplayName("save Tests")
    class SaveTests {

        /*@Test
        @DisplayName("Should save customer successfully")
        void shouldSaveSuccessfully() {
            Customer savedCustomer = new Customer("John Doe", Sex.M, LocalDate.of(1990, 1, 1), "john@example.com", "+1234567890");
            savedCustomer.setId(1L);

            when(customerFacade.exists(any(Customer.class))).thenReturn(Optional.empty());
            when(customerFacade.save(any(Customer.class))).thenReturn(savedCustomer);
            ResponseModel<CustomerModel> response = customerService.save(customerCreateModel);
            assertNotNull(response);
            assertNotNull(response.getBusinessCode());
            assertNotNull(response.getData());
            verify(customerFacade, times(1)).exists(any(Customer.class));
            verify(customerFacade, times(1)).save(any(Customer.class));
        }*/

        @Test
        @DisplayName("Should throw exception when customer already exists")
        void shouldThrowWhenExists() {
            when(customerFacade.exists(any(Customer.class))).thenReturn(Optional.of(customer));
            assertThrows(TemplateNotFoundException.class, () -> customerService.save(customerCreateModel));
            verify(customerFacade, never()).save(any(Customer.class));
        }

        /*@Test
        @DisplayName("Should verify existence before saving")
        void shouldVerifyBeforeSave() {
            Customer savedCustomer = new Customer("John Doe", Sex.M, LocalDate.of(1990, 1, 1), "john@example.com", "+1234567890");
            savedCustomer.setId(1L);

            when(customerFacade.exists(any(Customer.class))).thenReturn(Optional.empty());
            when(customerFacade.save(any(Customer.class))).thenReturn(savedCustomer);
            customerService.save(customerCreateModel);
            verify(customerFacade, times(1)).exists(any(Customer.class));
        }*/

        @Test
        @DisplayName("Should not save when customer exists")
        void shouldNotSaveWhenExists() {
            when(customerFacade.exists(any(Customer.class))).thenReturn(Optional.of(customer));
            assertThrows(TemplateNotFoundException.class, () -> customerService.save(customerCreateModel));
            verify(customerFacade, never()).save(any(Customer.class));
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle very large customer ID")
        void shouldHandleLargeId() {
            when(customerFacade.findCustomerById(Long.MAX_VALUE)).thenReturn(Optional.empty());
            assertThrows(TemplateNotFoundException.class, () -> customerService.getCustomerById(Long.MAX_VALUE));
        }

        @Test
        @DisplayName("Should handle zero ID")
        void shouldHandleZeroId() {
            when(customerFacade.findCustomerById(0L)).thenReturn(Optional.empty());
            assertThrows(TemplateNotFoundException.class, () -> customerService.getCustomerById(0L));
        }

        @Test
        @DisplayName("Should handle negative ID")
        void shouldHandleNegativeId() {
            when(customerFacade.findCustomerById(-1L)).thenReturn(Optional.empty());
            assertThrows(TemplateNotFoundException.class, () -> customerService.getCustomerById(-1L));
        }

        @Test
        @DisplayName("Should handle single item page")
        void shouldHandleSinglePage() {
            Pageable singlePageable = PageRequest.of(0, 1);
            Page<Customer> page = new PageImpl<>(List.of(customer), singlePageable, 1);
            when(customerFacade.findAllCustomers(singlePageable)).thenReturn(page);
            ResponseModel<CustomerModel> response = customerService.findAllCustomers(singlePageable);
            assertNotNull(response);
        }

        @Test
        @DisplayName("Should handle special characters in name")
        void shouldHandleSpecialChars() {
            Customer specialCustomer = new Customer("José García-López", Sex.M, LocalDate.of(1990, 1, 1), "jose@example.com", "+1234567890");
            specialCustomer.setId(1L);
            when(customerFacade.findCustomerById(1L)).thenReturn(Optional.of(specialCustomer));
            ResponseModel<CustomerModel> response = customerService.getCustomerById(1L);
            assertNotNull(response);
        }
    }

    @Nested
    @DisplayName("Mock Verification Tests")
    class MockVerificationTests {

        @Test
        @DisplayName("Should verify multiple get calls")
        void shouldVerifyMultipleCalls() {
            when(customerFacade.findCustomerById(1L)).thenReturn(Optional.of(customer));
            customerService.getCustomerById(1L);
            customerService.getCustomerById(1L);
            verify(customerFacade, times(2)).findCustomerById(1L);
        }

        @Test
        @DisplayName("Should verify multiple findAll calls")
        void shouldVerifyMultipleFindAllCalls() {
            Page<Customer> page = new PageImpl<>(List.of(customer), pageable, 1);
            when(customerFacade.findAllCustomers(pageable)).thenReturn(page);
            customerService.findAllCustomers(pageable);
            customerService.findAllCustomers(pageable);
            customerService.findAllCustomers(pageable);
            verify(customerFacade, times(3)).findAllCustomers(pageable);
        }

        @Test
        @DisplayName("Should verify save never called on duplicate")
        void shouldVerifyNoSaveOnDuplicate() {
            when(customerFacade.exists(any(Customer.class))).thenReturn(Optional.of(customer));
            assertThrows(TemplateNotFoundException.class, () -> customerService.save(customerCreateModel));
            verify(customerFacade, never()).save(any(Customer.class));
        }

        @Test
        @DisplayName("Should verify no extra interactions after exception")
        void shouldVerifyNoExtraInteractions() {
            when(customerFacade.findCustomerById(99L)).thenReturn(Optional.empty());
            assertThrows(TemplateNotFoundException.class, () -> customerService.getCustomerById(99L));
            verifyNoMoreInteractions(customerFacade);
        }
    }

    @Nested
    @DisplayName("Response Tests")
    class ResponseTests {

        @Test
        @DisplayName("Should have complete response for get")
        void shouldHaveCompleteResponseForGet() {
            when(customerFacade.findCustomerById(1L)).thenReturn(Optional.of(customer));
            ResponseModel<CustomerModel> response = customerService.getCustomerById(1L);
            assertNotNull(response.getData());
            assertNotNull(response.getBusinessCode());
            assertNotNull(response.getMessage());
            assertNotNull(response.getTraceId());
        }

        @Test
        @DisplayName("Should have complete response for findAll")
        void shouldHaveCompleteResponseForFindAll() {
            Page<Customer> page = new PageImpl<>(List.of(customer), pageable, 1);
            when(customerFacade.findAllCustomers(pageable)).thenReturn(page);
            ResponseModel<CustomerModel> response = customerService.findAllCustomers(pageable);
            assertNotNull(response.getData());
            assertNotNull(response.getBusinessCode());
            assertNotNull(response.getMessage());
        }
    }

    @Nested
    @DisplayName("Exception Tests")
    class ExceptionTests {

        @Test
        @DisplayName("Should throw when customer not found")
        void shouldThrowWhenNotFound() {
            when(customerFacade.findCustomerById(99L)).thenReturn(Optional.empty());
            assertThrows(TemplateNotFoundException.class, () -> customerService.getCustomerById(99L));
        }

        @Test
        @DisplayName("Should throw when customer exists during save")
        void shouldThrowWhenExistsDuringSave() {
            when(customerFacade.exists(any(Customer.class))).thenReturn(Optional.of(customer));
            assertThrows(TemplateNotFoundException.class, () -> customerService.save(customerCreateModel));
        }

        @Test
        @DisplayName("Should throw when findAll returns empty")
        void shouldThrowWhenFindAllEmpty() {
            Page<Customer> emptyPage = new PageImpl<>(List.of(), pageable, 0);
            when(customerFacade.findAllCustomers(pageable)).thenReturn(emptyPage);
            assertThrows(TemplateNotFoundException.class, () -> customerService.findAllCustomers(pageable));
        }
    }
}









