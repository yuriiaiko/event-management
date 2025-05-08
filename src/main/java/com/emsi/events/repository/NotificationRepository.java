package com.emsi.events.repository;

import com.emsi.events.model.entity.Notification;
import com.emsi.events.model.entity.Personne;
import com.emsi.events.model.enums.EnumTypeNotif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByDestinataire(Personne destinataire);
    List<Notification> findByType(EnumTypeNotif type);
}
