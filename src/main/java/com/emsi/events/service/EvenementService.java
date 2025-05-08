package com.emsi.events.service;

import com.emsi.events.model.entity.Club;
import com.emsi.events.model.entity.Evenement;
import com.emsi.events.repository.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EvenementService {
    @Autowired
    private EvenementRepository evenementRepository;

    public List<Evenement> findAll() {
        return evenementRepository.findAll();
    }

    public Optional<Evenement> findById(String id) {
        return evenementRepository.findById(id);
    }

    public List<Evenement> findByClub(Club club) {
        return evenementRepository.findByClub(club);
    }

    public List<Evenement> findUpcomingEvents(){
        return evenementRepository.findByDateAfter(LocalDateTime.now());
    }

    public List<Evenement> findByEstPayant(boolean estPayant){
        return evenementRepository.findByEstPayant(estPayant);
    }

    public void deleteById(String id) {
        evenementRepository.deleteById(id);
    }

    public Evenement save(Evenement evenement) {
        if (evenement.getId() == null || evenement.getId().isEmpty()) {
            evenement.setId(UUID.randomUUID().toString());
        }
        return evenementRepository.save(evenement);
    }
}
