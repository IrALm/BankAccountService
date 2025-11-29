package com.example.BanqueApp.service;

import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.model.createDTO.CreateCustomerDTO;
import com.example.BanqueApp.model.readDTO.CustomerDTO;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(CreateCustomerDTO customerDTO, String password);
    
    void finalizeCustomerProfile(Customer customer);
    
    CustomerDTO getCustomerById(Long id);
    
    List<CustomerDTO> getAllCustomers();
}
