package com.apisoft.customer.service;

import com.apisoft.customer.dao.CustomerDao;
import com.apisoft.customer.dao.entity.Customer;
import com.apisoft.customer.exception.BadRequestException;
import com.apisoft.customer.exception.Errors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Customer ms busing logic.
 *
 * @author Salah Abu Msameh
 */
@Service
@Transactional
public class CustomerMsService {

    private static final Log LOGGER = LogFactory.getLog(CustomerMsService.class);
    private static final String CUSTOMERS_CACHE = "customers";

    private CustomerDao customerDao;

    /**
     * constructor.
     * @param customerDao customer dao component
     */
    public CustomerMsService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * Get all customers.
     *
     * @return customers list
     */
    @Cacheable(cacheNames = CUSTOMERS_CACHE)
    public List<Customer> getCustomers() {
        LOGGER.info("Getting customersssss from DB");
        return this.customerDao.findAll();
    }

    /**
     * Retrieve customer for the given id.
     *
     * @param customerId customer id which will be used to fetch the customer.
     * @return customer info
     * @throws BadRequestException when no customer found for the given id
     */
    @Cacheable(cacheNames = CUSTOMERS_CACHE, key = "#customerId")
    public Customer getCustomer(final Long customerId) throws BadRequestException {

        LOGGER.info("Getting customer from DB");
        return this.customerDao.findById(customerId)
                .orElseThrow(() -> new BadRequestException(Errors.NO_CUSTOMER_FOUND, customerId));
    }

    /**
     * add a new customer.
     *
     * @param customer customer info object
     * @return added customer
     */
    @CachePut(cacheNames = CUSTOMERS_CACHE, key = "#customerId")
    public Customer addCustomer(final Customer customer) {

        customer.setCreatedDate(LocalDate.now());
        return this.customerDao.save(customer);
    }
    
    /**
     * updates customer info.
     *
     * @param customer new customer info object
     * @return updated customer
     * @throws BadRequestException when no customer found for the given id
     */
    @CachePut(cacheNames = CUSTOMERS_CACHE, key = "#customerId")
    public Customer updateCustomer(final Customer customer) throws  BadRequestException {
        
        Customer originalCustomer = getCustomer(customer.getCustomerId());
        
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
     * Delete customer.
     *
     * @param customerId customer id which will be used to fetch the customer.
     * @return deleted customer
     * @throws BadRequestException when no customer found for the given id
     */
    @CacheEvict(cacheNames = CUSTOMERS_CACHE, key = "#customerId")
    public Customer deleteCustomer(final Long customerId) throws BadRequestException {
    
        Customer customer = getCustomer(customerId);
        customerDao.delete(customer);
        return customer;
    }
}
