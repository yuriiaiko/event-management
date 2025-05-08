package com.emsi.events.model.entity;

import com.emsi.events.model.enums.EnumStatut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Inscription {
    @Id
    private String id;
    private LocalDateTime dateInscription;

    @Enumerated (EnumType.STRING)
    private EnumStatut statut;

    private boolean estPayante;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "evenement_id")
    private Evenement evenement;

    @OneToOne(mappedBy = "inscription")
    private Paiement paiement;

    public Inscription getInscription() {
        return this;
    }


}
