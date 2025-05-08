package com.emsi.events.controller;

import com.emsi.events.model.entity.Club;
import com.emsi.events.model.entity.Evenement;
import com.emsi.events.service.ClubService;
import com.emsi.events.service.EvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private ClubService clubService;

    @GetMapping("/evenements/upcoming")
    public List<Evenement> getUpcomingEvents() {
        return evenementService.findUpcomingEvents();
    }

    @GetMapping("/clubs")
    public List<ClubDTO> getClubs() {
        return clubService.findAll().stream()
                .map(club -> new ClubDTO(
                        club.getId(),
                        club.getNom(),
                        club.getMembres() != null ? club.getMembres().size() : 0,
                        club.getEvenements() != null ? club.getEvenements().size() : 0
                ))
                .collect(Collectors.toList());
    }

    // DTO pour éviter les problèmes de sérialisation JSON
    private static class ClubDTO {
        private final String id;
        private final String nom;
        private final int nombreMembres;
        private final int nombreEvenements;

        public ClubDTO(String id, String nom, int nombreMembres, int nombreEvenements) {
            this.id = id;
            this.nom = nom;
            this.nombreMembres = nombreMembres;
            this.nombreEvenements = nombreEvenements;
        }

        public String getId() { return id; }
        public String getNom() { return nom; }
        public int getNombreMembres() { return nombreMembres; }
        public int getNombreEvenements() { return nombreEvenements; }
    }
} 