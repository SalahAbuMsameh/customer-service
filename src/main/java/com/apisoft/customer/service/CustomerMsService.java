package com.apisoft.customer.service;

import com.apisoft.customer.exception.BadRequestException;
import com.apisoft.customer.exception.CustomerMsException;
import com.apisoft.customer.dao.CustomerDao;
import com.apisoft.customer.dao.entity.Customer;
import com.apisoft.customer.exception.Errors;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Customer ms busing logic.
 *
 * @author Salah Abu Msameh
 */
@Service
@Transactional
public class CustomerMsService {

    private CustomerDao customerDao;

    /**
     * constructor.
     * @param customerDao customer dao component
     */
    public CustomerMsService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * Get all customers.
     *
     * @return customers list
     */
    public List<Customer> getCustomers() {
        return this.customerDao.findAll();
    }

    /**
     * Retrieve customer for the given id.
     *
     * @param customerId customer id which will be used to fetch the customer.
     * @return customer info
     * @throws BadRequestException when no customer found for the given id
     */
    public Customer getCustomer(Long customerId) throws BadRequestException {

        return this.customerDao.findById(customerId)
                .orElseThrow(() -> new BadRequestException(Errors.NO_CUSTOMER_FOUND, customerId));
    }

    /**
     * add a new customer.
     *
     * @param customer customer info object
     * @return added customer
     */
    public Customer addCustomer(Customer customer) {

        customer.setCreatedDate(new Date());
        this.customerDao.save(customer);

        return customer;
    }
}
