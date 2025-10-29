package com.example.BanqueApp.service;

import com.example.BanqueApp.model.createDTO.CreateCountDTO;
import com.example.BanqueApp.model.createDTO.CreatePersonDTO;
import com.example.BanqueApp.model.createDTO.CustomerWithCount;
import com.example.BanqueApp.model.readDTO.CustomerDTO;

import java.util.List;

public interface CustomerService {

    public CustomerDTO creationNouveauClient(CustomerWithCount customerWithCount);
    public List<CustomerDTO> tousLesClients();
}
