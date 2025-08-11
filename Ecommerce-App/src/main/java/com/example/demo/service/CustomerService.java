package com.example.demo.service;
import com.example.demo.repository.*;
import java.util.*;
import com.example.demo.model.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.exception.ResourceNotFoundException;
@Service
public class CustomerService {
	@Autowired
	CustomerRepository repository;
	public List<Customer> getAllCustomers(){
		List<Customer> customers=repository.findAll();
		return customers;
	}
	public void saveCustomer(Customer customer) {
		
		repository.save(customer);
		
		
	}
	public Customer getCustomerById(Long id) throws ResourceNotFoundException {
		
		Optional<Customer> c = repository.findById(id);
		if(c.isPresent()){
			return c.get();
		}else {
			throw new ResourceNotFoundException("Customer not found with '"+id+"'");
		}
		
	}
	public void deleteCustomer(Long id) {
		// TODO Auto-generated method stub
		
		repository.deleteById(id);
		
	}
}
