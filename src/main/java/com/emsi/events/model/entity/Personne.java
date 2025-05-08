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

    public String getType() {
        if (this instanceof Administrateur) {
            return "admin";
        } else if (this instanceof MembreClub) {
            return "membre";
        } else if (this instanceof Etudiant) {
            return "etudiant";
        }
        return "autre";
    }

    // Méthode de connexion
    public boolean connexion() {
        // Logique de connexion à implémenter
        return true;
    }
}

