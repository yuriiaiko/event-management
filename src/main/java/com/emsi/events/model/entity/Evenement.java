package com.emsi.events.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evenement {

    @Id
    @UuidGenerator
    private String id;

    private String titre;

    private String description;

    private LocalDateTime date;

    private String lieu;

    private boolean estPayant;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "evenement")
    private List<Inscription> inscriptions = new ArrayList<>();


}
