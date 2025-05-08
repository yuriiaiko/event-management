package com.emsi.events.service;

import com.emsi.events.model.entity.Etudiant;
import com.emsi.events.model.entity.Evenement;
import com.emsi.events.model.entity.Notification;
import com.emsi.events.model.entity.Personne;
import com.emsi.events.model.enums.EnumTypeNotif;
import com.emsi.events.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> findById(String id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> findByDestinataire(Personne destinataire){
        return notificationRepository.findByDestinataire(destinataire);
    }

    public Notification creerNotification(Personne destinataire, String contenu, EnumTypeNotif type){
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString());
        notification.setDestinataire(destinataire);
        notification.setContenu(contenu);
        notification.setType(type);
        notification.setDateEnvoi(LocalDateTime.now());
        return notificationRepository.save(notification);

    }

    public void envoyerNotificationInscription(Etudiant etudiant, Evenement evenement) {
        String contenu = "Vous êtes inscrit à l'événement: " + evenement.getTitre();
        creerNotification(etudiant, contenu, EnumTypeNotif.INSCRIPTION);
    }

    public void envoyerNotificationDesinscription(Etudiant etudiant, Evenement evenement) {
        String contenu = "Vous vous êtes désinscrit de l'événement: " + evenement.getTitre();
        creerNotification(etudiant, contenu, EnumTypeNotif.ANNULATION);
    }

    public void envoyerNotificationModification(Evenement evenement) {
        String contenu = "L'événement " + evenement.getTitre() + " a été modifié.";

        // Envoyer une notification à tous les étudiants inscrits
        evenement.getInscriptions().forEach(inscription -> {
            creerNotification(inscription.getEtudiant(), contenu, EnumTypeNotif.MODIFICATION);
        });
    }

    public void envoyerRappelEvenement(Evenement evenement) {
        String contenu = "Rappel: L'événement " + evenement.getTitre() + " aura lieu le " + evenement.getDate();

        // Envoyer un rappel à tous les étudiants inscrits
        evenement.getInscriptions().forEach(inscription -> {
            creerNotification(inscription.getEtudiant(), contenu, EnumTypeNotif.RAPPEL);
        });
    }
}
