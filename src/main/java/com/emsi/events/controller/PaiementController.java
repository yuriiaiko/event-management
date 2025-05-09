package com.emsi.events.controller;

import com.emsi.events.model.entity.Etudiant;
import com.emsi.events.model.entity.Evenement;
import com.emsi.events.model.entity.Inscription;
import com.emsi.events.model.entity.Personne;
import com.emsi.events.model.enums.EnumMethode;
import com.emsi.events.model.enums.EnumStatut;
import com.emsi.events.service.EvenementService;
import com.emsi.events.service.InscriptionService;
import com.emsi.events.service.PaiementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/paiements")
public class PaiementController {

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private PaiementService paiementService;

    @GetMapping("/creer")
    public String showPaiementForm(@RequestParam String evenementId, Model model, HttpSession session) {
        Optional<Evenement> evenementOpt = evenementService.findById(evenementId);
        Personne user = (Personne) session.getAttribute("user");

        if (evenementOpt.isPresent() && user instanceof Etudiant) {
            Evenement evenement = evenementOpt.get();
            Etudiant etudiant = (Etudiant) user;

            // Vérifier si l'étudiant est inscrit
            Optional<Inscription> inscriptionOpt = inscriptionService.findByEtudiantAndEvenement(etudiant, evenement);
            if (inscriptionOpt.isPresent()) {
                model.addAttribute("evenement", evenement);
                return "evenement/paiement";
            }
        }
        return "redirect:/evenements";
    }

    @PostMapping("/{id}/confirmer")
    public String confirmerPaiement(@PathVariable String id, HttpSession session) {
        Optional<Evenement> evenementOpt = evenementService.findById(id);
        Personne user = (Personne) session.getAttribute("user");

        if (evenementOpt.isPresent() && user instanceof Etudiant) {
            Evenement evenement = evenementOpt.get();
            Etudiant etudiant = (Etudiant) user;

            // Récupérer l'inscription
            Optional<Inscription> inscriptionOpt = inscriptionService.findByEtudiantAndEvenement(etudiant, evenement);
            if (inscriptionOpt.isPresent()) {
                Inscription inscription = inscriptionOpt.get();
                
                // Créer et confirmer le paiement
                paiementService.creerPaiement(inscription, 50.0, EnumMethode.PAYPAL);
                
                // Mettre à jour le statut de l'inscription
                inscriptionService.updateStatut(inscription, EnumStatut.CONFIRMEE);
            }
        }
        return "redirect:/evenements/" + id;
    }
} 