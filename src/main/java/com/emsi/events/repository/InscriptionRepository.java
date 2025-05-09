package com.emsi.events.repository;

import com.emsi.events.model.entity.Etudiant;
import com.emsi.events.model.entity.Evenement;
import com.emsi.events.model.entity.Inscription;
import com.emsi.events.model.enums.EnumStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, String> {
    List<Inscription> findByEtudiant(Etudiant etudiant);

    List<Inscription> findByEvenement(Evenement evenement);

    List<Inscription> findByStatut(EnumStatut statut);

    @Query("SELECT i FROM Inscription i WHERE i.etudiant = :etudiant AND i.evenement = :evenement ORDER BY i.dateInscription DESC")
    List<Inscription> findByEtudiantAndEvenement(@Param("etudiant") Etudiant etudiant, @Param("evenement") Evenement evenement);
}
