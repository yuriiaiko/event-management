package com.emsi.events.repository;

import com.emsi.events.model.entity.Club;
import com.emsi.events.model.entity.MembreClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembreClubRepository extends JpaRepository<MembreClub, String> {
    List<MembreClub> findByClub(Club club);
}
