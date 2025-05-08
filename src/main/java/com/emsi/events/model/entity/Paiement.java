package com.emsi.events.model.entity;

import com.emsi.events.model.enums.EnumMethode;
import com.emsi.events.model.enums.EnumStatut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Paiement {
    @Id
    private String id;
    private double montant;

    @Enumerated(EnumType.STRING)
    private EnumStatut statut;

    @Enumerated(EnumType.STRING)
    private EnumMethode methode;

    private String reference;

    @OneToOne
    @JoinColumn(name = "inscription_id")
    private Inscription inscription;

    public Paiement getPaiement() {
        return this;
    }

}
