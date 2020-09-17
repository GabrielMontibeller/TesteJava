package com.example.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerRepository repository;
	

	@Override
	public Customer save(Customer cust) {
		return repository.save(cust);
	}

	public List<Customer> getAllUsers(){
		List<Customer> custs = new ArrayList<Customer>();
		repository.findAll().forEach(cust -> custs.add(cust));
		return custs;
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
		
	}


}
