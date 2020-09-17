package com.example.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.Customer;
import com.example.api.dto.CustomerDTO;
import com.example.api.response.Response;
import com.example.api.service.CustomerService;


@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	
	@PostMapping
	public ResponseEntity<Response<CustomerDTO>> create(@Valid @RequestBody CustomerDTO dto, BindingResult result) {

		Response<CustomerDTO> response = new Response<CustomerDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		Customer cust = service.save(this.convertDtoToEntity(dto));

		response.setData(this.convertEntityToDto(cust));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<CustomerDTO>> update(@Valid @RequestBody CustomerDTO dto, BindingResult result){
		
		Response<CustomerDTO> response = new Response<CustomerDTO>();
		
		Optional<Customer> cust = service.findById(dto.getId());
		
		if(!cust.isPresent()) {
			result.addError(new ObjectError("Customer", "Customer não encontrado"));
		}
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));
			
			return ResponseEntity.badRequest().body(response);
		}
		
		Customer saved = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(saved));
		return ResponseEntity.ok().body(response);
	}
	
	
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
		Response<String> response = new Response<String>();

		Optional<Customer> cust = service.findById(id);

		if (!cust.isPresent()) {
			response.getErrors().add("Customer de id " + id + " não encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		service.deleteById(id);
		response.setData("Customer de id "+ id + " apagado com sucesso");
		return ResponseEntity.ok().body(response);
	}

	private Customer convertDtoToEntity(CustomerDTO dto) {
		Customer cust = new Customer();
		cust.setId(dto.getId());
		cust.setEmail(dto.getEmail());
		cust.setName(dto.getName());

		return cust;
	}

	private CustomerDTO convertEntityToDto(Customer cust) {
		CustomerDTO dto = new CustomerDTO();
		dto.setEmail(cust.getEmail());
		dto.setId(cust.getId());
		dto.setName(cust.getName());

		return dto;
	}

}
