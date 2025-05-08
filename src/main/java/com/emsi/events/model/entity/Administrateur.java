package com.emsi.events.model.entity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Administrateur extends Personne {

    public void gererProfil() {
        // Logique pour gérer les profils
    }

    public void gererEvenement() {
        // Logique pour gérer les événements
    }

    public void gererClub() {
        // Logique pour gérer les clubs
    }

}
