package com.emsi.events.controller;

import com.emsi.events.model.entity.Etudiant;
import com.emsi.events.model.entity.Inscription;
import com.emsi.events.model.entity.Notification;
import com.emsi.events.model.entity.Personne;
import com.emsi.events.service.InscriptionService;
import com.emsi.events.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/etudiant")
public class EtudiantController {

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) user;

            // Récupérer les inscriptions de l'étudiant
            List<Inscription> inscriptions = inscriptionService.findByEtudiant(etudiant);
            model.addAttribute("inscriptions", inscriptions);

            // Récupérer les notifications de l'étudiant
            List<Notification> notifications = notificationService.findByDestinataire(etudiant);
            model.addAttribute("notifications", notifications);

            return "etudiant/dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/inscriptions")
    public String inscriptions(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) user;

            // Récupérer les inscriptions de l'étudiant
            List<Inscription> inscriptions = inscriptionService.findByEtudiant(etudiant);
            model.addAttribute("inscriptions", inscriptions);

            return "etudiant/inscriptions";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/notifications")
    public String notifications(Model model, HttpSession session) {
        Personne user = (Personne) session.getAttribute("user");
        if (user instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) user;

            // Récupérer les notifications de l'étudiant
            List<Notification> notifications = notificationService.findByDestinataire(etudiant);
            model.addAttribute("notifications", notifications);

            return "etudiant/notifications";
        } else {
            return "redirect:/login";
        }
    }
}

