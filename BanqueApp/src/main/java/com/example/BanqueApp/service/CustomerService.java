package com.example.BanqueApp.service;

import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.model.createDTO.CreateCustomerDTO;
import com.example.BanqueApp.model.readDTO.BankAdvisorDTO;
import com.example.BanqueApp.model.readDTO.CustomerDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(CreateCustomerDTO customerDTO, String password);
    
    void finalizeCustomerProfile(Customer customer);
    
    CustomerDTO getCustomerById(Long id);
    
    List<CustomerDTO> getAllCustomers();

    List<CustomerDTO> findByAdvisorId(Long advisorId);

    BankAdvisorDTO findByBankAdvisorById(Long clientId);
}
