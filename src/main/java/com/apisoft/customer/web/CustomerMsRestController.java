package com.apisoft.customer.web;

import com.apisoft.customer.dto.CustomerResponse;
import com.apisoft.customer.exception.BadRequestException;
import com.apisoft.customer.exception.Errors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.apisoft.customer.dao.entity.Customer;
import com.apisoft.customer.exception.CustomerMsException;
import com.apisoft.customer.service.CustomerMsService;
import com.apisoft.customer.web.api.ApiResponse;
import com.apisoft.customer.web.api.ApiResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

/**
 * Customer microservice rest controller.
 *
 * @author Salah Abu Msameh
 */
@RestController
@RequestMapping(path = "/customers")
public class CustomerMsRestController {

    private static final Log LOGGER = LogFactory.getLog(CustomerMsRestController.class);

    private CustomerMsService customerMsSrv;

    /**
     * constructor.
     * @param customerMsSrv customer service object
     */
    public CustomerMsRestController(CustomerMsService customerMsSrv) {
        this.customerMsSrv = customerMsSrv;
    }

    /**
     * Get customers list.
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> getAllCustomers() throws CustomerMsException {

        try {
            return ApiResponseBuilder.ok(new CustomerResponse(this.customerMsSrv.getCustomers()));
        } catch (Exception ex) {
            LOGGER.error("", ex);
            throw new CustomerMsException(ex);
        }
    }

    /**
     * Get customer.
     *
     * @param customerId customer id which used to fetch the customer
     * @return
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(@PathVariable("customerId") Long customerId)
            throws CustomerMsException {

        try {
            return ApiResponseBuilder.ok(new CustomerResponse(this.customerMsSrv.getCustomer(customerId)));
        } catch (BadRequestException ex) {
            LOGGER.error(ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            LOGGER.error("", ex);
            throw new CustomerMsException(ex);
        }
    }

    /**
     * add new customer.
     *
     * @param customer customer info object
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> addCustomer(@Valid @RequestBody Customer customer)
            throws CustomerMsException {

        try {
            customer = this.customerMsSrv.addCustomer(customer);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{customerId}")//current method resource path
                    .buildAndExpand(customer.getCustomerId())
                    .toUri();

            return ApiResponseBuilder.created(location, new CustomerResponse(customer));
            
        } catch (Exception ex) {
            LOGGER.error("", ex);
            throw new CustomerMsException(ex);
        }
    }

    /**
     * update customer.
     *
     * @param customer customer info object
     * @return
     */
    @PutMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(@RequestBody Customer customer)
            throws CustomerMsException {

        try {
            Optional.of(customer)
                    .filter(c -> c.getCustomerId() > 0)
                    .orElseThrow(() -> new BadRequestException(Errors.REQUEST_FIELDS_VALIDATION_ERRORS, null));

            return ApiResponseBuilder.ok(new CustomerResponse(customerMsSrv.updateCustomer(customer)));
            
        } catch (BadRequestException ex) {
            LOGGER.error(ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            LOGGER.error("", ex);
            throw new CustomerMsException(ex);
        }
    }

    /**
     * Delete customer.
     *
     * @param customerId customer id which used to fetch the customer
     * @return
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> deleteCustomer(@PathVariable("customerId") Long customerId)
            throws CustomerMsException {

        try {
            return ApiResponseBuilder.ok(new CustomerResponse(this.customerMsSrv.getCustomer(customerId)));
        } catch (BadRequestException ex) {
            LOGGER.error(ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            LOGGER.error("", ex);
            throw new CustomerMsException(ex);
        }
    }
}
