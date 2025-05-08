package com.emsi.events.controller;

import com.emsi.events.model.entity.*;
import com.emsi.events.service.ClubService;
import com.emsi.events.service.EvenementService;
import com.emsi.events.service.InscriptionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private InscriptionService inscriptionService;

    @GetMapping
    public String listEvenements(Model model) {
        List<Evenement> evenements = evenementService.findUpcomingEvents();
        model.addAttribute("evenements", evenements);
        return "evenement/liste";
    }

    @GetMapping("/{id}")
    public String detailEvenement(@PathVariable String id, Model model, HttpSession session) {
        Optional<Evenement> evenementOpt = evenementService.findById(id);
        if (evenementOpt.isPresent()) {
            Evenement evenement = evenementOpt.get();
            model.addAttribute("evenement", evenement);

            // Vérifier si l'utilisateur est inscrit
            Personne user = (Personne) session.getAttribute("user");
            if (user instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) user;
                Optional<Inscription> inscription = inscriptionService.findByEtudiantAndEvenement(etudiant, evenement);
                model.addAttribute("estInscrit", inscription.isPresent());
            }

            return "evenement/detail";
        } else {
            return "redirect:/evenements";
        }
    }


    @GetMapping("/creer")
    public String showCreateForm(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof MembreClub) {
            MembreClub membre = (MembreClub) user;
            model.addAttribute("club", membre.getClub());
            model.addAttribute("evenement", new Evenement());
            return "evenement/form";
        } else {
            return "redirect:/evenements";
        }
    }

    @PostMapping("/creer")
    public String createEvenement(@ModelAttribute Evenement evenement, @RequestParam String clubId, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof MembreClub) {
            Optional<Club> clubOpt = clubService.findById(clubId);
            if (clubOpt.isPresent()) {
                Club club = clubOpt.get();
                evenement.setClub(club);
                evenementService.save(evenement);
                return "redirect:/evenements";
            }
        }
        return "redirect:/evenements";
    }

    @GetMapping("/{id}/modifier")
    public String showEditForm(@PathVariable String id, Model model, HttpSession session) {
        Optional<Evenement> evenementOpt = evenementService.findById(id);
        Personne user = (Personne) session.getAttribute("user");

        if (evenementOpt.isPresent() && user instanceof MembreClub) {
            Evenement evenement = evenementOpt.get();
            MembreClub membre = (MembreClub) user;

            // Vérifier si le membre appartient au club organisateur
            if (membre.getClub().getId().equals(evenement.getClub().getId())) {
                model.addAttribute("evenement", evenement);
                return "evenement/form";
            }
        }
        return "redirect:/evenements";
    }

    @PostMapping("/{id}/modifier")
    public String updateEvenement(@PathVariable String id, @ModelAttribute Evenement evenement, HttpSession session) {
        Optional<Evenement> evenementOpt = evenementService.findById(id);
        Personne user = (Personne) session.getAttribute("user");

        if (evenementOpt.isPresent() && user instanceof MembreClub) {
            Evenement existingEvenement = evenementOpt.get();
            MembreClub membre = (MembreClub) user;

            // Vérifier si le membre appartient au club organisateur
            if (membre.getClub().getId().equals(existingEvenement.getClub().getId())) {
                evenement.setId(id);
                evenement.setClub(existingEvenement.getClub());
                evenementService.save(evenement);
                return "redirect:/evenements/" + id;
            }
        }
        return "redirect:/evenements";
    }

    @PostMapping("/{id}/supprimer")
    public String deleteEvenement(@PathVariable String id, HttpSession session) {
        Optional<Evenement> evenementOpt = evenementService.findById(id);
        Personne user = (Personne) session.getAttribute("user");

        if (evenementOpt.isPresent() && (user instanceof MembreClub || user instanceof Administrateur)) {
            Evenement evenement = evenementOpt.get();

            if (user instanceof MembreClub) {
                MembreClub membre = (MembreClub) user;
                // Vérifier si le membre appartient au club organisateur
                if (!membre.getClub().getId().equals(evenement.getClub().getId())) {
                    return "redirect:/evenements";
                }
            }

            evenementService.deleteById(id);
        }
        return "redirect:/evenements";
    }

    @PostMapping("/{id}/inscription")
    public String inscrireEvenement(@PathVariable String id, HttpSession session) {
        Optional<Evenement> evenementOpt = evenementService.findById(id);
        Personne user = (Personne) session.getAttribute("user");

        if (evenementOpt.isPresent() && user instanceof Etudiant) {
            Evenement evenement = evenementOpt.get();
            Etudiant etudiant = (Etudiant) user;

            inscriptionService.inscrireEtudiant(etudiant, evenement);

            if (evenement.isEstPayant()) {
                return "redirect:/paiements/creer?evenementId=" + id;
            }
        }
        return "redirect:/evenements/" + id;
    }

    @PostMapping("/{id}/desinscription")
    public String desinscrireEvenement(@PathVariable String id, HttpSession session) {
        Optional<Evenement> evenementOpt = evenementService.findById(id);
        Personne user = (Personne) session.getAttribute("user");

        if (evenementOpt.isPresent() && user instanceof Etudiant) {
            Evenement evenement = evenementOpt.get();
            Etudiant etudiant = (Etudiant) user;

            inscriptionService.desinscrireEtudiant(etudiant, evenement);
        }
        return "redirect:/evenements/" + id;
    }
}
