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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}), @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(
			@Parameter(description = "ID of the user to be retrieved") @PathVariable UUID id) {
		Optional<UserResponse> user = userService.getUserById(id);
		return user.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Operation(summary = "Save a new User", description = "Create a new user in the system")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User saved successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}), @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)})
	@PostMapping("/save")
	public ResponseEntity<UserResponse> saveUser(
			@Parameter(description = "User object to be saved") @RequestBody UserRequest user) {
		Optional<UserResponse> savedUser = userService.saveUser(user);
		return savedUser.map(value -> ResponseEntity.ok().body(value))
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PostMapping(value = "/users/{keycloakId}/avatar", consumes = {"multipart/form-data"})
	@Operation(summary = "Upload User Avatar Image", description = "Allows you to upload or update a user's avatar image.")
	@ApiResponse(responseCode = "200", description = "Image uploaded successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
	@ApiResponse(responseCode = "400", description = "Invalid file type or error during processing", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
	@ApiResponse(responseCode = "409", description = "Duplicate image detected", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
	@ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
	public ResponseEntity<Void> uploadAvatar(
			@Parameter(description = "Keycloak ID of the user") @PathVariable String keycloakId,
			@Parameter(description = "Avatar image file", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(value = "image", required = true) MultipartFile imageFile) {

		try {
			// Check file type
			String contentType = imageFile.getContentType();
			if (!"image/jpeg".equalsIgnoreCase(contentType)) {
				return ResponseEntity.badRequest().body(null); // Invalid file type
			}

			// Validate file content
			byte[] image = imageFile.getBytes();
			if (image == null || image.length == 0) {
				return ResponseEntity.badRequest().body(null); // File is empty
			}

			// Validate file extension
			String originalFilename = imageFile.getOriginalFilename();
			if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".jpg")) {
				return ResponseEntity.badRequest().body(null); // Invalid file extension
			}

			// Proceed with uploading the avatar
			userService.uploadAvatar(keycloakId, image);
			return ResponseEntity.ok().build();
		} catch (IOException e) {
			return ResponseEntity.badRequest().build(); // Error during processing
		}
	}


	@Operation(summary = "Get User Avatar Image", description = "Retrieve a user's avatar image")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the user's avatar image"), @ApiResponse(responseCode = "404", description = "User not found")})
	@GetMapping("/users/{keycloakId}/avatar")
	public ResponseEntity<byte[]> getAvatar(
			@Parameter(description = "Keycloak ID of the user") @PathVariable String keycloakId) {
		Optional<byte[]> image = userService.getAvatar(keycloakId);

		if (image.isPresent()) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			headers.setContentDispositionFormData("attachment", "avatar_" + keycloakId + ".jpg");

			return new ResponseEntity<>(image.get(), headers, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


}
