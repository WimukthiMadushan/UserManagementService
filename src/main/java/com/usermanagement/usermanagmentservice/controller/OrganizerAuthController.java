package com.usermanagement.usermanagmentservice.controller;

import com.usermanagement.usermanagmentservice.dto.AuthResponse;
import com.usermanagement.usermanagmentservice.dto.MessageResponse;
import com.usermanagement.usermanagmentservice.dto.organizer.Organizer;
import com.usermanagement.usermanagmentservice.dto.organizer.OrganizerSignUpRequest;
import com.usermanagement.usermanagmentservice.dto.SignInRequest;
import com.usermanagement.usermanagmentservice.dto.traveller.Traveller;
import com.usermanagement.usermanagmentservice.service.OrganizerAuthService;
import com.usermanagement.usermanagmentservice.utility.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/organizer")
public class OrganizerAuthController {
    @Autowired
    private OrganizerAuthService organizerAuthService;

    @PostMapping("/signup")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Email is already in use",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    })
    public ResponseEntity<?> organizerSignUp(@Valid @RequestBody OrganizerSignUpRequest organizerSignUpRequest) {
        try {
            Organizer registeredOrganizer = organizerAuthService.registerOrganizer(organizerSignUpRequest);
            String token = JwtUtils.generateOrganizerToken(registeredOrganizer);

            AuthResponse authResponse = new AuthResponse(
                    "User registered successfully!",
                    token
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Could not register user. " + e.getMessage()));
        }
    }

    @PostMapping("/signin")
    @Operation(summary = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    })
    public ResponseEntity<?> organizerSignIn(@Valid @RequestBody SignInRequest signInRequest) {
        try {
            Optional<Organizer> userOptional = organizerAuthService.loginOrganizer(signInRequest);

            if (userOptional.isPresent()) {
                Organizer user = userOptional.get();
                String token = JwtUtils.generateOrganizerToken(user);

                AuthResponse authResponse = new AuthResponse(
                        "Login successfull!",
                        token
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
            } else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                        .body(new MessageResponse("Error: Invalid email or password."));
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: Login failed. " + e.getMessage()));
        }
    }
}
