package de.akogare.userwebservice.controller;

import de.akogare.userwebservice.requests.UserRequest;
import de.akogare.userwebservice.responses.UserResponse;
import de.akogare.userwebservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/user-webservice")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get User by ID", description = "Retrieve a user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID of the user to be retrieved") @PathVariable UUID id) {
        Optional<UserResponse> user = userService.getUserById(id);
        return user.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Save a new User", description = "Create a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User saved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<UserResponse> saveUser(
            @Parameter(description = "User object to be saved") @RequestBody UserRequest user) {
        Optional<UserResponse> savedUser = userService.saveUser(user);
        return savedUser.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Echo", description = "Echo the input")
    @GetMapping("/echo")
    public ResponseEntity<String> echo(
            @Parameter(description = "Input to be echoed") @RequestParam String input) {
        return ResponseEntity.ok().body(input);
    }
}
