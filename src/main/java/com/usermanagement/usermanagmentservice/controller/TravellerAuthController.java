package com.usermanagement.usermanagmentservice.controller;

import com.usermanagement.usermanagmentservice.dto.*;
import com.usermanagement.usermanagmentservice.dto.traveller.Traveller;
import com.usermanagement.usermanagmentservice.dto.traveller.TravellerSignUpRequest;
import com.usermanagement.usermanagmentservice.service.TravellerAuthService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/traveller")
public class TravellerAuthController {
    @Autowired
    private TravellerAuthService travellerAuthService;

    /**
     * Handles the user registration process.
     *
     * @param travellerSignUpRequest the request object containing user registration details
     * @return a ResponseEntity containing an AuthResponse with a success message and user details if registration is successful,
     *         or a MessageResponse with an error message if registration fails
     *
     * @throws RuntimeException if the email is already in use
     */
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
    public ResponseEntity<?> travellerSignUp(@Valid @RequestBody TravellerSignUpRequest travellerSignUpRequest) {
        try {
            Traveller registeredTraveller = travellerAuthService.registerTraveller(travellerSignUpRequest);
            String token = JwtUtils.generateTravellerToken(registeredTraveller);

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

    /**
     * Endpoint for user login (sign in).
     *
     * @param signInRequest The login request DTO, validated.
     * @return ResponseEntity with user details upon successful login or an error message.
     */
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
    public ResponseEntity<?> travellerSignIn(@Valid @RequestBody SignInRequest signInRequest) {
        try {
            Optional<Traveller> userOptional = travellerAuthService.loginTraveller(signInRequest);

            if (userOptional.isPresent()) {
                Traveller user = userOptional.get();
                String token = JwtUtils.generateTravellerToken(user);

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
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
                    .body(new MessageResponse("Error: Login failed. " + e.getMessage()));
        }
    }
}
