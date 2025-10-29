package com.example.BanqueApp.model.readDTO;

import java.time.LocalDate;
import java.util.List;


public record CustomerDTO(
        PersonDTO personDTO,
        List<CountDTO> sesComptesDTO
) {}
