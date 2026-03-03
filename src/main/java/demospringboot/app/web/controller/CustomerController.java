package demospringboot.app.web.controller;

import demospringboot.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static demospringboot.util.Constants.*;

@Tag(name = "Customer Controller", description = "APIs for Customer Management")
@RestController
@RequestMapping(Constants.API_BASE_PATH + Constants.API_VERSION + Constants.API_CUSTOMERS)
public class CustomerController {

    @Operation(summary = "Get Customer Data by ID", description = "Retrieve customer information from the system")
    @GetMapping(value = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of customer data"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Customer Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public String findById(@Parameter(description = "ID de Cliente", required = true, example = "1") @PathVariable int id) {
        System.out.println("getCustomer Endpoint Called");
        return "Customer data, from CustomerController Id:" + " " + id;
    }

}
