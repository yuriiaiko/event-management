package com.emsi.events.service;

import com.emsi.events.model.entity.Etudiant;
import com.emsi.events.model.entity.Evenement;
import com.emsi.events.model.entity.Inscription;
import com.emsi.events.model.enums.EnumStatut;
import com.emsi.events.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InscriptionService {
    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Inscription> findAll() {
        return inscriptionRepository.findAll();
    }

    private Optional<Inscription> findById(String id){
        return inscriptionRepository.findById(id);
    }

    public List<Inscription> findByEtudiant(Etudiant etudiant){
        return inscriptionRepository.findByEtudiant(etudiant);
    }

    private List<Inscription> findByEvenement(Evenement evenement){
        return inscriptionRepository.findByEvenement(evenement);
    }

    public Optional<Inscription> findByEtudiantAndEvenement(Etudiant etudiant, Evenement evenement){
        return inscriptionRepository.findByEtudiantAndEvenement(etudiant, evenement);
    }

    public Inscription inscrireEtudiant(Etudiant etudiant, Evenement evenement) {
        //Vérifier si l'étudiant est déjà inscrit
        Optional<Inscription> existingInscription = inscriptionRepository.findByEtudiantAndEvenement(etudiant, evenement);
        if (existingInscription.isPresent()) {
            return existingInscription.get();
        }
        //Créer une nouvelle inscription
        Inscription inscription = new Inscription();
        inscription.setId(UUID.randomUUID().toString());
        inscription.setEvenement(evenement);
        inscription.setEtudiant(etudiant);
        inscription.setDateInscription(LocalDateTime.now());
        inscription.setStatut(EnumStatut.EN_ATTENTE);
        inscription.setEstPayante(evenement.isEstPayant());

        // Sauvegarder l'inscription
        Inscription savedInscription = inscriptionRepository.save(inscription);

        // Envoyer une notification
        notificationService.envoyerNotificationInscription(etudiant, evenement);

        return savedInscription;
    }

    public boolean desinscrireEtudiant(Etudiant etudiant, Evenement evenement) {
        Optional<Inscription> inscriptionOpt = inscriptionRepository.findByEtudiantAndEvenement(etudiant, evenement);
        if (inscriptionOpt.isPresent()) {
            Inscription inscription = inscriptionOpt.get();
            //Verifier si l'évènement est gratuit
            if(!evenement.isEstPayant()){
                inscriptionRepository.delete(inscription);
            }
            //Envoyer une notification
            notificationService.envoyerNotificationDesinscription(etudiant, evenement);

            return true;
        }
        return false;
    }

    public Inscription updateStatut(Inscription inscription, EnumStatut statut) {
        inscription.setStatut(statut);
        return inscriptionRepository.save(inscription);
    }


}
