package de.akogare.userwebservice.requests;

import java.util.UUID;

public record UserRequest(UUID id, String email, String firstName, String lastName, String companyName,
                          String court, boolean isReady) {

}
