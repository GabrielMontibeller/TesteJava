package com.example.api.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {

	
	@MockBean
	CustomerRepository repository;
	
	@Autowired
	CustomerService service;
	
	
	private static final String NAME = "teste";
	private static final String EMAIL = "teste@email.com";
	
	@Test
	public void testSave() {
		BDDMockito.given(repository.save(Mockito.any(Customer.class))).willReturn(getMockCustomer());
		
		Customer response = service.save(new Customer());
		
		assertNotNull(response);
	}
	
	
	private Customer getMockCustomer() {
		Customer cust = new Customer();
		cust.setId(1L);
		cust.setEmail(EMAIL);
		cust.setName(NAME);
		
		return cust;
	}

}
