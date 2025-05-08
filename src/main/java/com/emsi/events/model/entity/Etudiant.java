package com.emsi.events.model.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data

public class Etudiant extends Personne {
    @OneToMany(mappedBy = "etudiant")
    private List<Inscription> inscriptions;
    public void inscriptionEvenement() {
        // Logique pour s'inscrire à un événement
    }

    public void consulterEvenement() {
        // Logique pour consulter un événement
    }

    public void listerEvenementInscrit() {
        // Logique pour lister les événements inscrits
    }

    public void visualiserOrganisateurs() {
        // Logique pour visualiser les organisateurs
    }

    public void desinscriptionEvenement() {
        // Logique pour se désinscrire d'un événement
    }
}
