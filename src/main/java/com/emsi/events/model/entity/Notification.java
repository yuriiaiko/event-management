package com.emsi.events.model.entity;

import com.emsi.events.model.enums.EnumTypeNotif;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications") // <-- Très important pour bien nommer la table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @Column(length = 36) // <-- Pour dire que c'est un String fixe type UUID par exemple
    private String id;

    @Column(nullable = false) // <-- Pas de contenu vide
    private String contenu;

    @Column(name = "date_envoi", nullable = false) // <-- Bien nommer et obligatoire
    private LocalDateTime dateEnvoi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // <-- Enum obligatoire
    private EnumTypeNotif type;

    @ManyToOne(fetch = FetchType.LAZY) // <-- Important pour éviter de charger toute la personne à chaque fois
    @JoinColumn(name = "personne_id", nullable = false) // <-- Clef étrangère, obligatoire
    private Personne destinataire;
}
