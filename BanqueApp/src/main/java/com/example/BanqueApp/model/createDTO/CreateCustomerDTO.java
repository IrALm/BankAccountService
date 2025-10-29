package com.example.BanqueApp.model.createDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

/* En écriture seule */
public record CreateCustomerDTO(
        @NotNull( message = " A un compte est associé une seule personne")
        CreatePersonDTO personDTO,
        @NotEmpty(message = "Un client doit avoir au moins un compte qui lui est associé")
        @Valid List<CreateCountDTO> sesComptesDTO
) {}
