package demospringboot.app.facade;

import demospringboot.app.domain.entity.Customer;
import demospringboot.app.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Component
@Transactional(readOnly = true)
public class CustomerFacade {


    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerFacade(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }



    public Optional<Customer> findCustomerById(long id) {
        return customerRepository.findById(id);
    }

    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> exists(Customer customer) {
        return  customerRepository.findByNameAndBirthDateAndEmail(customer.getName(), customer.getBirthDate(), customer.getEmail());
    }

    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}
