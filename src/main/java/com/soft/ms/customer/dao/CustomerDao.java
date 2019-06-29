package com.soft.ms.customer.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.soft.ms.customer.dao.entity.Customer;

/**
 * 
 * @author Salah Abu Msameh
 */
@Repository
public interface CustomerDao extends CrudRepository<Customer, Long> {

}
