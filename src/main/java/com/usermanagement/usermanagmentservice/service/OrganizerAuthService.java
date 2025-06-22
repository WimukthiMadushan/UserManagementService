package com.usermanagement.usermanagmentservice.service;

import com.usermanagement.usermanagmentservice.dto.SignInRequest;
import com.usermanagement.usermanagmentservice.dto.organizer.Organizer;
import com.usermanagement.usermanagmentservice.dto.organizer.OrganizerRepository;
import com.usermanagement.usermanagmentservice.dto.organizer.OrganizerSignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrganizerAuthService {
    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Organizer registerOrganizer(OrganizerSignUpRequest organizerSignUpRequest) {
        if (organizerRepository.existsByEmail(organizerSignUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        Organizer user = new Organizer();
        user.setOrganizationName(organizerSignUpRequest.getOrganizationName());
        user.setOwnerName(organizerSignUpRequest.getOwnerName());
        user.setDateOfEstablishment(organizerSignUpRequest.getDateOfEstablishment());
        user.setCity(organizerSignUpRequest.getCity());
        user.setCountry(organizerSignUpRequest.getCountry());
        user.setContactNumber(organizerSignUpRequest.getContactNumber());
        user.setEmail(organizerSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(organizerSignUpRequest.getPassword()));

        return organizerRepository.save(user);
    }

    public Optional<Organizer> loginOrganizer(SignInRequest signInRequest) {
        Optional<Organizer> userOptional = organizerRepository.findByEmail(signInRequest.getEmail());

        if (userOptional.isPresent()) {
            Organizer user = userOptional.get();
            if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
