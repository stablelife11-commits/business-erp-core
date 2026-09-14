package com.business.erp.repository;

import com.business.erp.entity.Customer;
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