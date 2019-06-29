package com.soft.ms.customer.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.ms.customer.dao.entity.Customer;
import com.soft.ms.customer.service.CustomerServiceService;

/**
 * 
 * @author Salah Abu Msameh
 */
@RestController
@RequestMapping(path = "/customers")
public class CustomerRestController {

	@Autowired
	private CustomerServiceService customerSrv;
	
	/**
	 * 
	 * @return
	 */
	@GetMapping({"", "/"})
	public ResponseEntity<List<Customer>> getCustomers() {
		return ResponseEntity.ok(this.customerSrv.getCustomers());
	}
	
	/**
	 * 
	 * @return
	 */
	@PostMapping({"", "/"})
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		this.customerSrv.addCustomer(customer);
		return ResponseEntity.ok(customer);
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long customerId) {
		return ResponseEntity.ok(this.customerSrv.getCustomer(customerId));
	} 
}
