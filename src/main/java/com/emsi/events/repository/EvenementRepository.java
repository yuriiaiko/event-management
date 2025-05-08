package com.emsi.events.repository;

import com.emsi.events.model.entity.Club;
import com.emsi.events.model.entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, String> {
    List<Evenement> findByDateAfter(LocalDateTime now);

    List<Evenement> findByEstPayant(boolean estPayant);

    List<Evenement> findByClub(Club club);
}
