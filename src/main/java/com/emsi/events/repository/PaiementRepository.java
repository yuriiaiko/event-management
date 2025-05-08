package com.emsi.events.repository;

import com.emsi.events.model.entity.Inscription;
import com.emsi.events.model.entity.Paiement;
import com.emsi.events.model.enums.EnumMethode;
import com.emsi.events.model.enums.EnumStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, String> {
    Optional<Paiement> findByInscription(Inscription inscription);
    List<Paiement> findByStatut(EnumStatut statut);
    List<Paiement> findByMethode(EnumMethode methode);
}
