package demospringboot.app.web.controller;

import demospringboot.app.service.CustomerService;
import demospringboot.app.web.model.CustomerCreateModel;
import demospringboot.app.web.model.CustomerModel;
import demospringboot.app.web.model.ResponseModel;
import demospringboot.util.Constants;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Objects;

import static demospringboot.util.Constants.*;

@Api(value = "CustomerController", tags = {"Customer Management"})
@RestController
@RequestMapping(API_BASE_PATH + API_VERSION + API_CUSTOMERS)
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Get Customer Data by ID", notes = "Retrieve customer information from the system")
    @GetMapping(value = "/{id}")
    @ApiResponses(value = { @ApiResponse(code = CODE_SUCCESFULL_OK, message = "Successful retrieval of customer data"),
            @ApiResponse(code = CODE_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = CODE_NOT_AUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = CODE_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = CODE_NOT_FOUND, message = "Customer Not Found"),
            @ApiResponse(code = CODE_INTERNAL_ERROR, message = "Internal Server Error")
    })
    public ResponseEntity<ResponseModel<CustomerModel>> findById(
            @Parameter(description = "ID de Cliente", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @ApiOperation(value = "Create a new Customer", notes = "This endpoint allows you to create a new customer in the system.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiResponses(value = { @ApiResponse(code = CODE_SUCCESFULL_CREATED, message = "Customer created successfully", responseHeaders = {
            @ResponseHeader(name = HttpHeaders.LOCATION, response = URL.class, description = "URI of the created customer resource")
        }),
            @ApiResponse(code = CODE_BAD_REQUEST, message = "Invalid input data"),
            @ApiResponse(code = CODE_NOT_AUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = CODE_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = CODE_INTERNAL_ERROR, message = "Internal Server Error")})
    public ResponseEntity<ResponseModel<CustomerModel>> save(@Valid @RequestBody CustomerCreateModel customerModel) {

        var response = customerService.save(customerModel);
        Long id = Objects.nonNull(response.getData()) ? response.getData().getId() : BigDecimal.ZERO.longValue();

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(id)
                                .toUri()
                )
                .body(response);

    }

    @ApiOperation(value = "Get All Customers", notes = "Retrieve a paginated list of all customers in the system.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = { @ApiResponse(code = CODE_SUCCESFULL_OK, message = "Successful retrieval of customers data"),
            @ApiResponse(code = CODE_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = CODE_NOT_AUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = CODE_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = CODE_NOT_FOUND, message = "No Customers Found"),
            @ApiResponse(code = CODE_INTERNAL_ERROR, message = "Internal Server Error")})
    public ResponseEntity<ResponseModel<CustomerModel>> findAll(
            @ApiIgnore("Ignore pageable parameters in Swagger documentation")
            @PageableDefault(value = 1, size = 10, page = 0, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(customerService.findAllCustomers(pageable));
    }

}
