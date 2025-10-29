package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Count;
import com.example.BanqueApp.entity.TypeCompte;
import com.example.BanqueApp.model.readDTO.CountDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-29T19:20:11+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
public class CountMapperImpl implements CountMapper {

    @Override
    public CountDTO toDTO(Count count) {
        if ( count == null ) {
            return null;
        }

        String numeroCompte = null;
        TypeCompte typeCompte = null;
        BigDecimal solde = null;

        numeroCompte = count.getNumeroCompte();
        typeCompte = count.getTypeCompte();
        solde = count.getSolde();

        CountDTO countDTO = new CountDTO( numeroCompte, typeCompte, solde );

        return countDTO;
    }

    @Override
    public Count toEntity(CountDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Count.CountBuilder count = Count.builder();

        count.numeroCompte( dto.numeroCompte() );
        count.typeCompte( dto.typeCompte() );
        count.solde( dto.solde() );

        return count.build();
    }

    @Override
    public List<CountDTO> toDTOList(List<Count> users) {
        if ( users == null ) {
            return null;
        }

        List<CountDTO> list = new ArrayList<CountDTO>( users.size() );
        for ( Count count : users ) {
            list.add( toDTO( count ) );
        }

        return list;
    }

    @Override
    public List<Count> toEntityList(List<CountDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Count> list = new ArrayList<Count>( dtos.size() );
        for ( CountDTO countDTO : dtos ) {
            list.add( toEntity( countDTO ) );
        }

        return list;
    }
}
