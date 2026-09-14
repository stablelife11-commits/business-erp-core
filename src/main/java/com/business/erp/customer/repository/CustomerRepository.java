package com.business.erp.customer.repository;

import com.business.erp.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByMobile(String mobile);

    boolean existsByMobile(String mobile);
    List<Customer> findByNameContainingIgnoreCase(String name);
}
