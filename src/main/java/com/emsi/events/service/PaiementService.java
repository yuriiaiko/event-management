package com.emsi.events.service;

import com.emsi.events.model.entity.Inscription;
import com.emsi.events.model.entity.Paiement;
import com.emsi.events.model.enums.EnumMethode;
import com.emsi.events.model.enums.EnumStatut;
import com.emsi.events.repository.InscriptionRepository;
import com.emsi.events.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaiementService {
    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;
    @Autowired
    private InscriptionService inscriptionService;

    public List<Paiement> findAll(){
        return paiementRepository.findAll();
    }

    public Optional<Paiement> findById(String id){
        return paiementRepository.findById(id);
    }

    public Optional<Paiement> findByInscription(Inscription inscription){
        return paiementRepository.findByInscription(inscription);
    }

    public Paiement creerPaiement(Inscription inscription, double montant, EnumMethode methode){
        Paiement paiement = new Paiement();
        paiement.setId(UUID.randomUUID().toString());
        paiement.setInscription(inscription);
        paiement.setMontant(montant);
        paiement.setMethode(methode);
        paiement.setStatut(EnumStatut.EN_ATTENTE);
        paiement.setReference("PAY-" + UUID.randomUUID().toString().substring(0, 8));
        return paiementRepository.save(paiement);
        

    }

    public Paiement confirmerPaiement(Paiement paiement){
        paiement.setStatut(EnumStatut.CONFIRMEE);
        Paiement savedPaiement = paiementRepository.save(paiement);

        // Mettre à jour le statut de l'inscription
        Inscription inscription = paiement.getInscription();
        inscriptionService.updateStatut(inscription, EnumStatut.CONFIRMEE);
        return savedPaiement;
    }

    public Paiement annulerPaiement(Paiement paiement){
        paiement.setStatut(EnumStatut.ANNULEE);
        Paiement savedPaiement = paiementRepository.save(paiement);

        // Mettre a jour le statut de l'inscription
        Inscription inscription = paiement.getInscription();
        inscriptionService.updateStatut(inscription, EnumStatut.ANNULEE);
        return savedPaiement;
    }

}
