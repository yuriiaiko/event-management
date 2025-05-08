package com.emsi.events.model.entity;

import com.emsi.events.model.enums.EnumRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MembreClub extends Etudiant {
    @Enumerated(EnumType.STRING)
    private EnumRole role;


    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    public void creerEvenement() {
        // Logique pour créer un événement
    }

    public void modifierEvenement() {
        // Logique pour modifier un événement
    }

    public void supprimerEvenement() {
        // Logique pour supprimer un événement
    }

}
