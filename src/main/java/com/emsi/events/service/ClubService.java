package com.emsi.events.service;

import com.emsi.events.model.entity.Club;
import com.emsi.events.model.entity.MembreClub;
import com.emsi.events.repository.ClubRepository;
import com.emsi.events.repository.MembreClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClubService {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private MembreClubRepository membreClubRepository;

    public List<Club> findAll(){
        return clubRepository.findAll();
    }

    public Optional<Club> findById(String id) {
        return clubRepository.findById(id);
    }

    public Club save(Club club) {
        if (club.getId() == null || club.getId().isEmpty()) {
            club.setId(UUID.randomUUID().toString());
        }
        return clubRepository.save(club);
    }

    public void deleteById(String id) {
        clubRepository.deleteById(id);
    }

    public List<MembreClub> findMembresByClub(Club club) {
        return membreClubRepository.findByClub(club);
    }
}
