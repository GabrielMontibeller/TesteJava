package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerRepository repository;
	
	@Value("${pagination.customers_per_page}")
	private int customerPerPage;

	@Override
	public Customer save(Customer cust) {
		return repository.save(cust);
	}

	@Override
	public Page<Customer> findAllPage(Customer cust, int page) {
		
		@SuppressWarnings("deprecation")
		PageRequest pg = new PageRequest(page, customerPerPage);
		
		return repository.findAllPage(cust, pg);
	}

}
