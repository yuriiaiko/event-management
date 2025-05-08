package com.emsi.events.controller;

import com.emsi.events.model.entity.Administrateur;
import com.emsi.events.model.entity.Club;
import com.emsi.events.model.entity.Evenement;
import com.emsi.events.model.entity.Personne;
import com.emsi.events.service.ClubService;
import com.emsi.events.service.EvenementService;
import com.emsi.events.service.InscriptionService;
import com.emsi.events.service.PersonneService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PersonneService personneService;

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private InscriptionService inscriptionService;
    @Autowired
    private ClubService clubService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if(user instanceof Administrateur) {
            // Récupérer les statistiques
            long nbPersonnes = personneService.findAll().size();
            long nbClubs = clubService.findAll().size();
            long nbEvenements = evenementService.findAll().size();
            long nbInscriptions = inscriptionService.findAll().size();

            model.addAttribute("nbPersonnes", nbPersonnes);
            model.addAttribute("nbClubs", nbClubs);
            model.addAttribute("nbEvenements", nbEvenements);
            model.addAttribute("nbInscriptions", nbInscriptions);

            return"admin/dashboard";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/personnes")
    public String personnes(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Administrateur){
            List<Personne> personnes = personneService.findAll();
            model.addAttribute("personnes", personnes);
            return "admin/personnes";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/clubs")
    public String clubs(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Administrateur) {
            List<Club> clubs = clubService.findAll();
            model.addAttribute("clubs", clubs);
            return "admin/clubs";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/evenements")
    public String evenements(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Administrateur) {
            List<Evenement> evenements = evenementService.findAll();
            model.addAttribute("evenements", evenements);
            return "admin/evenements";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/clubs/creer")
    public String showCreateClubForm(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Administrateur) {
            model.addAttribute("club", new Club());
            return "admin/club-form";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/clubs/creer")
    public String createClub(@ModelAttribute Club club, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Administrateur) {
            clubService.save(club);
            return "redirect:/admin/clubs";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/clubs/{id}/modifier")
    public String showEditClubForm(@PathVariable String id, Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Administrateur) {
            Optional<Club> clubOpt = clubService.findById(id);
            if (clubOpt.isPresent()) {
                model.addAttribute("club", clubOpt.get());
                return "admin/club-form";
            }
            return "redirect:/admin/clubs";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/clubs/{id}/modifier")
    public String updateClub(@PathVariable String id, @ModelAttribute Club club, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Administrateur) {
            club.setId(id);
            clubService.save(club);
            return "redirect:/admin/clubs";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/clubs/{id}/supprimer")
    public String deleteClub(@PathVariable String id, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Administrateur) {
            clubService.deleteById(id);
            return "redirect:/admin/clubs";
        } else {
            return "redirect:/login";
        }
    }
}
