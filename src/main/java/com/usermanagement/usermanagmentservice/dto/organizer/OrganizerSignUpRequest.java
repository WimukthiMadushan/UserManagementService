package com.usermanagement.usermanagmentservice.dto.organizer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerSignUpRequest {
    @NotBlank(message = "Organization name cannot be blank")
    @Size(max = 50, message = "Organization name must be at most 50 characters")
    private String organizationName;

    @NotBlank(message = "Owner name cannot be blank")
    @Size(max = 50, message = "Owner name must be at most 50 characters")
    private String ownerName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 120, message = "Password must be at most 120 characters")
    private String password;

    @NotBlank(message = "Contact number cannot be blank")
    @Size(min = 12, max = 12, message = "Contact number must be exactly 12 characters")
    private String contactNumber;

    @NotBlank(message = "Country cannot be blank")
    @Size(max = 50, message = "Country must be at most 50 characters")
    private String country;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 50, message = "City must be at most 50 characters")
    private String city;

    @NotNull(message = "Date of establishment cannot be null")
    private LocalDate dateOfEstablishment; // Expecting yyyy-MM-dd format

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDateOfEstablishment() {
        return dateOfEstablishment;
    }

    public void setDateOfEstablishment(LocalDate dateOfEstablishment) {
        this.dateOfEstablishment = dateOfEstablishment;
    }
}
