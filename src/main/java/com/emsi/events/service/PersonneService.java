package com.emsi.events.service;

import com.emsi.events.model.entity.Personne;
import com.emsi.events.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    public List<Personne> findAll() {
        return personneRepository.findAll();
    }

    public Optional<Personne> findById(String id) {
        return personneRepository.findById(id);
    }

    public Optional<Personne> findByEmail(String email) {
        return personneRepository.findByEmail(email);
    }

    public Personne save(Personne personne) {
        return personneRepository.save(personne);
    }

    public void deletebyId(String id) {
        personneRepository.deleteById(id);
    }

    public boolean authenticate(String email, String password) {
        Optional<Personne> personneOpt = personneRepository.findByEmail(email);
        if (personneOpt.isPresent()) {
            Personne personne = personneOpt.get();
            return personne.getMotDePasse().equals(password);

        }
        return false;
    }

}
