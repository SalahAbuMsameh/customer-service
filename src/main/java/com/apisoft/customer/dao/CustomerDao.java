package com.apisoft.customer.dao;

import com.apisoft.customer.dao.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for all db interaction for the {@link Customer} type only.
 *
 * @author Salah Abu Msameh
 */
@Repository
public class CustomerDao implements DaoModel<Customer> {

    private EntityManager entityManager;
    
    /**
     * constructor.
     * @param entityManager configured entity manager
     */
    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(this.entityManager.find(Customer.class, id));
    }

    @Override
    public List<Customer> findAll() {
        return this.entityManager
                .createQuery("FROM Customer", Customer.class)
                .getResultList();
    }

    @Override
    public void save(Customer entity) {
        this.entityManager.persist(entity);
    }

    @Override
    public void update(Customer entity) {
        this.entityManager.merge(entity);
    }

    @Override
    public void delete(Customer entity) {
        this.entityManager.remove(entity);
    }
}
