package com.arnaldoneto.webservices.customersadministration.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.arnaldoneto.webservices.customersadministration.bean.Customer;
import com.arnaldoneto.webservices.customersadministration.service.CustomerDetailService;

import br.com.arnaldoneto.CustomerDetail;
import br.com.arnaldoneto.DeleteCustomerRequest;
import br.com.arnaldoneto.DeleteCustomerResponse;
import br.com.arnaldoneto.GetAllCustomerDetailRequest;
import br.com.arnaldoneto.GetAllCustomerDetailResponse;
import br.com.arnaldoneto.GetCustomerDetailRequest;
import br.com.arnaldoneto.GetCustomerDetailResponse;

@Endpoint
public class CustomerDetailEndpoint {

	@Autowired
	CustomerDetailService service;
	
	@PayloadRoot(namespace="http://arnaldoneto.com.br", localPart="GetCustomerDetailRequest")
	@ResponsePayload
	public GetCustomerDetailResponse processCustomerDetailRequest(@RequestPayload GetCustomerDetailRequest req) throws Exception{
		Customer customer = service.findById(req.getId());
		if(customer == null) {
			throw new Exception("Invalid Customer id " + req.getId());
		}
		return convertToGetCustomerDetailResponse(customer);
	}
	
	private GetCustomerDetailResponse convertToGetCustomerDetailResponse(Customer customer) {
		GetCustomerDetailResponse resp = new GetCustomerDetailResponse();
		resp.setCustomerDetail(convertToCustomerDetail(customer));
		return resp;
	}
	
	private CustomerDetail convertToCustomerDetail(Customer customer) {
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(customer.getId());
		customerDetail.setName(customer.getName());
		customerDetail.setPhone(customer.getPhone());
		customerDetail.setEmail(customer.getEmail());
		return customerDetail;
	}
	
	@PayloadRoot(namespace="http://arnaldoneto.com.br", localPart="GetAllCustomerDetailRequest")
	@ResponsePayload
	public GetAllCustomerDetailResponse processGetAllCustomerDetailRequest(@RequestPayload GetAllCustomerDetailRequest req) {
		List<Customer> customers = service.findAll();
		return convertToGetAllCustomerDetailResponse(customers);
	}
	
	private GetAllCustomerDetailResponse convertToGetAllCustomerDetailResponse(List<Customer> customers) {
		GetAllCustomerDetailResponse resp = new GetAllCustomerDetailResponse();
		customers.stream().forEach(c -> resp.getCustomerDetail().add(convertToCustomerDetail(c)));
		return resp;
	}
	
	@PayloadRoot(namespace="http://arnaldoneto.com.br", localPart="DeleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse deleteCustomerRequest(@RequestPayload DeleteCustomerRequest req) {
		DeleteCustomerResponse resp = new DeleteCustomerResponse();
		resp.setStatus(convertStatusSoap(service.deleteById(req.getId())));
		return resp;
	}
	
	private br.com.arnaldoneto.Status convertStatusSoap(com.arnaldoneto.webservices.customersadministration.helper.Status statusService){
		if(statusService == com.arnaldoneto.webservices.customersadministration.helper.Status.FAILURE) {
			return br.com.arnaldoneto.Status.FAILURE;
		}
		return br.com.arnaldoneto.Status.SUCCESS;
	}
	
	
	
	
}
