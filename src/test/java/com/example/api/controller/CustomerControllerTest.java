package com.example.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.api.domain.Customer;
import com.example.api.dto.CustomerDTO;
import com.example.api.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerControllerTest {
	
	@MockBean
	CustomerService service;
	
	@Autowired
	MockMvc mvc;
	
	private static final Long ID = 1L;
	private static final String NAME = "nome";
	private static final String EMAIL = "teste@email.com";
	private static final String URL = "/customers";
	
	@Test
	public void testSave() throws Exception{
		 BDDMockito.given(service.save(Mockito.any(Customer.class))).willReturn(getMockCustomer());
		 
		 mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
				 .contentType(MediaType.APPLICATION_JSON)
				 .accept(MediaType.APPLICATION_JSON))
		 .andExpect(status().isCreated())
		 .andExpect(jsonPath("$.data.id").value(ID))
		 .andExpect(jsonPath("$.data.name").value(NAME))
		 .andExpect(jsonPath("$.data.email").value(EMAIL));
		 
	}
	
	@Test
	public void testUpdate() throws Exception{
		String name = "novo name";
		
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(getMockCustomer()));
		BDDMockito.given(service.save(Mockito.any(Customer.class))).willReturn(new Customer(1L, name, EMAIL));
		
		mvc.perform(MockMvcRequestBuilders.put(URL).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.name").value(name))
		.andExpect(jsonPath("$.data.email").value(EMAIL));
	}
	
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception{
		BDDMockito.given(service.findById(Mockito.anyLong())).willReturn(Optional.of(new Customer()));
		
		mvc.perform(MockMvcRequestBuilders.delete(URL+"/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").value("Customer de id "+ ID + " apagado com sucesso"));
		
		
	}
	
	private Customer getMockCustomer() {
		Customer cust = new Customer(1L, NAME, EMAIL);
		return cust;
	}
	
	public String getJsonPayload() throws JsonProcessingException {
		
		CustomerDTO dto = new CustomerDTO();
		dto.setEmail(EMAIL);
		dto.setId(ID);
		dto.setName(NAME);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
		
	}
	
	

}
