package com.usermanagement.usermanagmentservice.dto.organizer;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "organizer", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Organization name cannot be blank")
    @Size(min = 2, max = 50, message = "Organization name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String organizationName;

    @NotBlank(message = "Owner name cannot be blank")
    @Size(min = 2, max = 50, message = "Owner name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String ownerName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 120, message = "Password must be between 6 and 120 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Contact number cannot be blank")
    @Size(min = 12, max = 12, message = "Contact number must be exactly 12 characters")
    @Pattern(regexp = "\\d{12}", message = "Contact number must be 12 digits")
    @Column(length = 12, nullable = false)
    private String contactNumber;

    @NotBlank(message = "Country cannot be blank")
    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
    @Column(nullable = false)
    private String country;

    @NotBlank(message = "City cannot be blank")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    @Column(nullable = false)
    private String city;

    @NotNull(message = "Date of establishment cannot be null")
    @Past(message = "Date of establishment must be in the past")
    @Column(nullable = false)
    private LocalDate dateOfEstablishment;
}
