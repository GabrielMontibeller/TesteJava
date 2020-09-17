package com.example.api.service;



import java.util.List;
import java.util.Optional;

import com.example.api.domain.Customer;

public interface CustomerService {
	
     Customer save(Customer cust);
     
     Optional<Customer> findById(Long id);
     
     void deleteById(Long id);

	 List<Customer> getAllUsers();
     

}
