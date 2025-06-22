package com.usermanagement.usermanagmentservice.dto.traveller;

// import com.example.yourproject.model.User; // Adjust import if User class is in a different package
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravellerRepository extends JpaRepository<Traveller, Long> {
    Optional<Traveller> findByEmail(String email);
    Boolean existsByEmail(String email);
}