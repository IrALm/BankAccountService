package com.example.BanqueApp.Mapper;

import com.example.BanqueApp.entity.Count;
import com.example.BanqueApp.entity.Transaction;
import com.example.BanqueApp.entity.TypeCompte;
import com.example.BanqueApp.entity.TypeTransaction;
import com.example.BanqueApp.model.readDTO.CountDTO;
import com.example.BanqueApp.model.readDTO.TransactionDTO;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T01:27:52+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionDTO toDTO(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TypeTransaction typetransaction = null;
        BigDecimal montant = null;
        CountDTO compteSource = null;
        CountDTO compteDest = null;

        typetransaction = transaction.getTypetransaction();
        montant = transaction.getMontant();
        compteSource = countToCountDTO( transaction.getCompteSource() );
        compteDest = countToCountDTO( transaction.getCompteDest() );

        TransactionDTO transactionDTO = new TransactionDTO( typetransaction, montant, compteSource, compteDest );

        return transactionDTO;
    }

    @Override
    public Transaction toEntity(TransactionDTO transactionDTO) {
        if ( transactionDTO == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setTypetransaction( transactionDTO.typetransaction() );
        transaction.setMontant( transactionDTO.montant() );
        transaction.setCompteSource( countDTOToCount( transactionDTO.compteSource() ) );
        transaction.setCompteDest( countDTOToCount( transactionDTO.compteDest() ) );

        return transaction;
    }

    protected CountDTO countToCountDTO(Count count) {
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

    protected Count countDTOToCount(CountDTO countDTO) {
        if ( countDTO == null ) {
            return null;
        }

        Count.CountBuilder count = Count.builder();

        count.numeroCompte( countDTO.numeroCompte() );
        count.typeCompte( countDTO.typeCompte() );
        count.solde( countDTO.solde() );

        return count.build();
    }
}
