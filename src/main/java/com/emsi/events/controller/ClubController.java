package com.emsi.events.controller;

import com.emsi.events.model.entity.Club;
import com.emsi.events.model.entity.Evenement;
import com.emsi.events.model.entity.MembreClub;
import com.emsi.events.model.entity.Personne;
import com.emsi.events.service.ClubService;
import com.emsi.events.service.EvenementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private EvenementService evenementService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof MembreClub) {
            MembreClub membre = (MembreClub) user;
            Club club = membre.getClub();

            // Récupérer les événements du club
            List<Evenement> evenements = evenementService.findByClub(club);
            model.addAttribute("evenements", evenements);

            // Récupérer les membres du club
            List<MembreClub> membres = clubService.findMembresByClub(club);
            model.addAttribute("membres", membres);

            model.addAttribute("club", club);

            return "club/dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/evenements")
    public String evenements(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof MembreClub) {
            MembreClub membre = (MembreClub) user;
            Club club = membre.getClub();

            // Récupérer les événements du club
            List<Evenement> evenements = evenementService.findByClub(club);
            model.addAttribute("evenements", evenements);
            model.addAttribute("club", club);

            return "club/evenements";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/membres")
    public String membres(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof MembreClub) {
            MembreClub membre = (MembreClub) user;
            Club club = membre.getClub();

            // Récupérer les membres du club
            List<MembreClub> membres = clubService.findMembresByClub(club);
            model.addAttribute("membres", membres);
            model.addAttribute("club", club);

            return "club/membres";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/liste")
    public String listeClubs(Model model) {
        List<Club> clubs = clubService.findAll();
        model.addAttribute("clubs", clubs);
        return "club/liste";
    }

    @GetMapping("/{id}")
    public String detailClub(@PathVariable String id, Model model) {
        Optional<Club> clubOpt = clubService.findById(id);
        if (clubOpt.isPresent()) {
            Club club = clubOpt.get();
            model.addAttribute("club", club);

            // Récupérer les membres du club
            List<MembreClub> membres = clubService.findMembresByClub(club);
            model.addAttribute("membres", membres);

            // Récupérer les événements du club
            List<Evenement> evenements = evenementService.findByClub(club);
            model.addAttribute("evenements", evenements);

            return "club/detail";
        } else {
            return "redirect:/club/liste";
        }
    }
}
