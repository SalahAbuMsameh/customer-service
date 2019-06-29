package com.soft.ms.customer.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.ms.customer.dao.CustomerDao;
import com.soft.ms.customer.dao.entity.Customer;

/**
 * 
 * @author Salah Abu Msameh
 */
@Service
public class CustomerServiceService {

	@Autowired
	private CustomerDao customerDao;
	
	/**
	 * 
	 * @return
	 */
	public List<Customer> getCustomers() {
		return StreamSupport.stream(this.customerDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param customer
	 */
	public void addCustomer(Customer customer) {
		customerDao.save(customer);
	}
	
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public Customer getCustomer(long customerId) {
		return this.customerDao.findById(customerId).get();
	}
}
