package com.emsi.events.repository;

import com.emsi.events.model.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, String> {
    Optional<Personne> findByEmail(String email);
}
