package com.example.BanqueApp.service.impl;

import com.example.BanqueApp.entity.MatriculeCounter;
import com.example.BanqueApp.repository.MatriculeCounterRepository;
import com.example.BanqueApp.service.MatriculeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatriculeServiceImpl implements MatriculeService {

    private final MatriculeCounterRepository counterRepository;

    public synchronized String generateMatricule(){

        MatriculeCounter counter =
                counterRepository.findById(1L).orElseGet(() ->{
                    MatriculeCounter c = MatriculeCounter.builder()
                            .id(1L)
                            .lastNumber(0)
                            .build();
                    return counterRepository.save(c);
                });

        int newNumber = counter.getLastNumber() + 1;
        counter.setLastNumber(newNumber);
        counterRepository.save(counter);

        String formatted = String.format("%02d" , newNumber);
        return "BK" + formatted;
    }
}
