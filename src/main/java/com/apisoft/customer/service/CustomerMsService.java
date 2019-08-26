package com.apisoft.customer.service;

import com.apisoft.customer.exception.BadRequestException;
import com.apisoft.customer.exception.CustomerMsException;
import com.apisoft.customer.dao.CustomerDao;
import com.apisoft.customer.dao.entity.Customer;
import com.apisoft.customer.exception.Errors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        return loadCustomer(customerId)
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
    
    /**
     * updates customer info.
     *
     * @param customer new customer info object
     * @return
     */
    public Customer updateCustomer(Customer customer) throws  CustomerMsException {
        
        Long customerId = customer.getCustomerId();
        
        Customer originalCustomer = loadCustomer(customerId)
                .orElseThrow(() -> new BadRequestException(Errors.NO_CUSTOMER_FOUND, customerId));
        
        originalCustomer.setName(!StringUtils.isEmpty(customer.getName()) ? customer.getName() : originalCustomer.getName());
        originalCustomer.setAddressLine1(!StringUtils.isEmpty(customer.getAddressLine1()) ? customer.getAddressLine1() :
                originalCustomer.getAddressLine1());
        originalCustomer.setAddressLine2(!StringUtils.isEmpty(customer.getAddressLine2()) ? customer.getAddressLine2() :
                originalCustomer.getAddressLine2());
        originalCustomer.setEmail(!StringUtils.isEmpty(customer.getEmail()) ? customer.getEmail() :
                originalCustomer.getEmail());
        originalCustomer.setMobileNo(!StringUtils.isEmpty(customer.getMobileNo()) ? customer.getMobileNo() :
                originalCustomer.getMobileNo());
        
        //save customer to db
        this.customerDao.save(originalCustomer);
        
        return originalCustomer;
    }
    
    /**
     * load customer from the database.
     *
     * @param customerId customer id which will be used to fetch the customer.
     * @return
     */
    private Optional<Customer> loadCustomer(Long customerId) {
        return this.customerDao.findById(customerId);
    }
}
