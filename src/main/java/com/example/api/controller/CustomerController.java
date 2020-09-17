package com.example.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
//	@DeleteMapping(value = "/{id}")
//	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
//		Response<String> response = new Response<String>();
//
//		Optional<WalletItem> wi = service.findById(walletItemId);
//
//		if (!wi.isPresent()) {
//			response.getErrors().add("WalletItem de id " + walletItemId + " não encontrada");
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//		}
//		
//		service.deleteById(walletItemId);
//		response.setData("WalletItem de id "+ walletItemId + " apagada com sucesso");
//		return ResponseEntity.ok().body(response);
//	}

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
