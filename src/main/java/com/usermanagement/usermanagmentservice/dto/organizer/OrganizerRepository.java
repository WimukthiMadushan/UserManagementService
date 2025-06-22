package com.usermanagement.usermanagmentservice.dto.organizer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    Optional<Organizer> findByEmail(String email);
    Boolean existsByEmail(String email);
}
