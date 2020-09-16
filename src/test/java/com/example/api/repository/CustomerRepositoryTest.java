package com.example.api.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.api.domain.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {
	
	private static final String NAME = "teste";
	private static final String EMAIL = "teste@email.com";
	private Long savedCustomerId = null;
	
	@Autowired
	CustomerRepository repository;
	
	@Before
	public void setUp() {
		Customer customer = new Customer(null, NAME, EMAIL);
	    repository.save(customer);
		savedCustomerId = customer.getId();
		
	}
	
	@After
	public void tearDown() {
		repository.deleteAll();
	}
	
	@Test
	public void testSave() {
		
		Customer customer = new Customer(1L, NAME, EMAIL);
		Customer response = repository.save(customer);
		assertNotNull(response);
	}
	
	@Test
	public void testUpdate() {
		Optional<Customer> customer = repository.findById(savedCustomerId);
		String name = "nome alterado";
		
		Customer changed = customer.get();
		changed.setName(name);
		
		repository.save(changed);
		
		Optional<Customer> newCustomer = repository.findById(savedCustomerId);
		assertEquals(name, newCustomer.get().getName());
	}
	
	@Test
	public void testDelete() {
		Customer cust = new Customer(null, NAME, EMAIL);
		
		repository.save(cust);
		
		repository.deleteById(cust.getId());
		
		Optional<Customer> response = repository.findById(cust.getId());
		assertFalse(response.isPresent());
	}
	
	
	
	

}
