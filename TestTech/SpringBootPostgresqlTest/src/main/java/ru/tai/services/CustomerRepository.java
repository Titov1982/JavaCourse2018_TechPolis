package ru.tai.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tai.model.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByFirstName(String firstName);
    List<Customer> findByLastName(String lastName);
}
