package com.arnaldoneto.webservices.customersadministration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.arnaldoneto.webservices.customersadministration.bean.Customer;
import com.arnaldoneto.webservices.customersadministration.helper.Status;

@Component
public class CustomerDetailService {

	private static List<Customer> customers = new ArrayList<>();
	
	static {
		Customer customer1 = new Customer(1, "Bob", "999999999", "bob@gmail.com");
		customers.add(customer1);
		
		Customer customer2 = new Customer(2, "Mark", "88888888", "mark@gmail.com");
		customers.add(customer2);
		
		Customer customer3 = new Customer(3, "Jose", "77777777", "jose@gmail.com");
		customers.add(customer3);
		
		Customer customer4 = new Customer(4, "Klay", "666666666", "klay@gmail.com");
		customers.add(customer4);
	}
	
	public Customer findById(int id) {
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		if(customerOptional.isPresent()) {
			return customerOptional.get();
		}
		return null;
	}
	
	public List<Customer> findAll(){
		return customers;
	}
	
	public Status deleteById(int id) {
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		if(customerOptional.isPresent()) {
			customers.remove(customerOptional.get());
			return Status.SUCCESS;
		}
		return Status.FAILURE;
	}
	
}














