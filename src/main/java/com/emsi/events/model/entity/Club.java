package com.emsi.events.model.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Club {
    @Id
    @UuidGenerator
    private String id;

    private String nom;

    @OneToMany(mappedBy = "club")
    private List<MembreClub> membres = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<Evenement> evenements = new ArrayList<>();
}
