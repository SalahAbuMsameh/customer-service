package com.apisoft.customer.dto;

import com.apisoft.customer.dao.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A wrapper dto to add the customer root properties.
 *
 * @author Salah Abu Msameh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {

    private Customer customer;
    private List<Customer> customers;
    
    public CustomerResponse() {
    }

    /**
     * constructor.
     * @param customer customer which will represents the response body
     */
    public CustomerResponse(Customer customer) {
        this.customer = customer;
    }

    /**
     * constructor.
     * @param customers customers list which will represents the response body
     */
    public CustomerResponse(List<Customer> customers) {
        this.customers = customers;
    }

    @JsonProperty("customer")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonProperty("customers")
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
