package com.usermanagement.usermanagmentservice.service;

import java.util.Optional;

import com.usermanagement.usermanagmentservice.dto.*;
import com.usermanagement.usermanagmentservice.dto.organizer.OrganizerSignUpRequest;
import com.usermanagement.usermanagmentservice.dto.traveller.Traveller;
import com.usermanagement.usermanagmentservice.dto.traveller.TravellerRepository;
import com.usermanagement.usermanagmentservice.dto.traveller.TravellerSignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TravellerAuthService {
    @Autowired
    private TravellerRepository travellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional // Ensures the operation is atomic
    public Traveller registerTraveller(TravellerSignUpRequest travellerSignUpRequest) {
        if (travellerRepository.existsByEmail(travellerSignUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        Traveller user = new Traveller();
        user.setFirstName(travellerSignUpRequest.getFirstName());
        user.setLastName(travellerSignUpRequest.getLastName());
        user.setDateOfBirth(travellerSignUpRequest.getDateOfBirth());
        user.setEmail(travellerSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(travellerSignUpRequest.getPassword()));

        return travellerRepository.save(user);
    }

    public Optional<Traveller> loginTraveller(SignInRequest signInRequest) {
        Optional<Traveller> userOptional = travellerRepository.findByEmail(signInRequest.getEmail());

        if (userOptional.isPresent()) {
            Traveller user = userOptional.get();
            if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}