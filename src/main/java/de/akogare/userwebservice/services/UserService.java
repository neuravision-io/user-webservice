package de.akogare.userwebservice.services;

import de.akogare.userwebservice.entity.Avatar;
import de.akogare.userwebservice.entity.User;
import de.akogare.userwebservice.repositories.AvatarRepository;
import de.akogare.userwebservice.repositories.UserRepository;
import de.akogare.userwebservice.requests.UserRequest;
import de.akogare.userwebservice.responses.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final AvatarRepository avatarRepository;

	public UserService(UserRepository userRepository, AvatarRepository avatarRepository) {
		this.userRepository = userRepository;
		this.avatarRepository = avatarRepository;
	}

	public Optional<UserResponse> saveUser(UserRequest user) {
		User userEntity = new User(user.id(),
		                           user.email(),
		                           user.firstName(),
		                           user.lastName(),
		                           user.companyName(),
		                           user.court(),
		                           user.isReady());

		User savedEntity = userRepository.save(userEntity);

		UserResponse response = new UserResponse(savedEntity.getId(),
		                                         savedEntity.getEmail(),
		                                         savedEntity.getFirstName(),
		                                         savedEntity.getLastName(),
		                                         savedEntity.getCompanyName(),
		                                         savedEntity.getCourt(),
		                                         savedEntity.isReady());
		return Optional.of(response);
	}

	public Optional<UserResponse> getUserById(UUID id) {
		Optional<User> user = userRepository.findById(id);
		return user.map(value -> new UserResponse(value.getId(),
		                                          value.getEmail(),
		                                          value.getFirstName(),
		                                          value.getLastName(),
		                                          value.getCompanyName(),
		                                          value.getCourt(),
		                                          value.isReady()));
	}


	@Transactional
	public void uploadAvatar(String keycloakId, byte[] image) {
		Avatar avatar = avatarRepository.findByKeycloakId(keycloakId).orElse(new Avatar(keycloakId, image));
		avatar.setImage(image);
		avatarRepository.save(avatar);
	}

	@Transactional
	public Optional<byte[]> getAvatar(String keycloakId) {
		return avatarRepository.findByKeycloakId(keycloakId).map(Avatar::getImage);
	}
}
