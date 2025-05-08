package com.emsi.events.model.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne {
    @Id
    @UuidGenerator
    private String id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private boolean estMembreClub;

    // Méthode de connexion
    public boolean connexion() {
        // Logique de connexion à implémenter
        return true;
    }
}

