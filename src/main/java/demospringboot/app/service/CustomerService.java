package demospringboot.app.service;

import brave.Span;
import brave.Tracer;
import demospringboot.app.domain.repository.CustomerRepository;
import demospringboot.app.exception.TemplateNotFoundException;
import demospringboot.app.facade.CustomerFacade;
import demospringboot.app.web.controller.CustomerController;
import demospringboot.app.web.model.CustomerCreateModel;
import demospringboot.app.web.model.CustomerModel;
import demospringboot.app.web.model.ResponseModel;
import demospringboot.util.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static demospringboot.app.web.model.CustomerCreateModel.FN_MODEL_TO_ENTITY;

import static demospringboot.app.web.model.CustomerModel.FN_ENTITY_TO_MODEL;
import static demospringboot.util.FlowCode.GET_CUSTOMER;
import static demospringboot.util.FlowCode.SAVE_CUSTOMER;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomerService {

    private final CustomerFacade customerFacade;
    private final Tracer tracer;

//    @Autowired
//    public CustomerService(CustomerFacade customerFacade, Tracer tracer) {
//        this.customerFacade = customerFacade;
//        this.tracer = tracer;
//    }

    public ResponseModel<CustomerModel> getCustomerById(long id) {

        var customer = customerFacade.findCustomerById(id);
        if(customer.isEmpty()){
            throw new TemplateNotFoundException(ResponseCode.AT_TS_003);
        }

        log.info("[{}] Customer found: {}", GET_CUSTOMER::code, customer::get);

        return ResponseModel.builder(tracer)
                .code(ResponseCode.AT_TS_002)
                .data(FN_ENTITY_TO_MODEL.apply(customer.get()))
                .build();
    }

    public ResponseModel<CustomerModel> findAllCustomers(Pageable pageable) {

        var customers = customerFacade.findAllCustomers(pageable);
        if(customers.isEmpty()){
            throw new TemplateNotFoundException(ResponseCode.AT_TS_003);
        }

        log.info("[{}] Customers found: {}", GET_CUSTOMER::code, customers::getTotalElements);

        return ResponseModel.builder(tracer)
                .code(ResponseCode.AT_TS_002)
                .data(customers.map(FN_ENTITY_TO_MODEL))
                .build();
    }

    public ResponseModel<CustomerModel> save(CustomerCreateModel model){
        final var customer = FN_MODEL_TO_ENTITY.apply(model);
        if(customerFacade.exists(customer).isPresent()){
            throw new TemplateNotFoundException(ResponseCode.AT_TS_004);
        }

        customerFacade.save(customer);
        log.info("[{}] Customer created: {}", SAVE_CUSTOMER::code, customer::getId);

        return ResponseModel.builder(tracer)
                .code(ResponseCode.AT_TS_001)
                .data(FN_ENTITY_TO_MODEL.apply(customer))
                .build();

    }

}
