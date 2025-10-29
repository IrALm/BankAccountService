package com.example.BanqueApp.model.createDTO;

import com.example.BanqueApp.model.readDTO.PersonDTO;

public record CustomerWithCount(
        CreatePersonDTO client,
        CreateCountDTO compte
) {}
