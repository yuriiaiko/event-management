package com.emsi.events.controller;

import com.emsi.events.model.entity.MembreClub;
import com.emsi.events.model.entity.Personne;
import com.emsi.events.model.entity.Administrateur;
import com.emsi.events.service.PersonneService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private PersonneService personneService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        boolean authenticated = personneService.authenticate(email, password);
        if (authenticated) {
            Personne personne = personneService.findByEmail(email).get();
            session.setAttribute("user", personne);

            // Vérification du type d'utilisateur avec instanceof
            if (personne instanceof Administrateur) {
                return "redirect:/admin/dashboard";
            } else if (personne instanceof MembreClub) {
                return "redirect:/club/dashboard";
            } else {
                return "redirect:/etudiant/dashboard";
            }
        } else {
            model.addAttribute("error", "Email ou mot de passe incorrect");
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
