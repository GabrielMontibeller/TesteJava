package com.example.api.service;

import org.springframework.data.domain.Page;

import com.example.api.domain.Customer;

public interface CustomerService {
	
     Customer save(Customer cust);
     Page<Customer> findAllPage(Customer cust, int page);

}
