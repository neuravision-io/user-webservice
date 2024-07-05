package de.akogare.userwebservice.responses;

import java.util.UUID;

public record UserResponse(UUID id, String email, String firstName, String lastName, String companyName,
                           String court) {

}
