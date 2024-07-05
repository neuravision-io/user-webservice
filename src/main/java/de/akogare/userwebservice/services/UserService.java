package de.akogare.userwebservice.services;

import de.akogare.userwebservice.entity.User;
import de.akogare.userwebservice.repositories.UserRepository;
import de.akogare.userwebservice.requests.UserRequest;
import de.akogare.userwebservice.responses.UserResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserResponse> saveUser(UserRequest user) {
        User userEntity = new User(user.id(), user.email(), user.firstName(), user.lastName(),
                user.companyName(), user.court());

        User savedEntity = userRepository.save(userEntity);

        UserResponse response = new UserResponse(savedEntity.getId(), savedEntity.getEmail(),
                savedEntity.getFirstName(), savedEntity.getLastName(), savedEntity.getCompanyName(),
                savedEntity.getCourt());
        return Optional.of(response);
    }

    public Optional<UserResponse> getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new UserResponse(value.getId(), value.getEmail(), value.getFirstName(),
                value.getLastName(), value.getCompanyName(), value.getCourt()));
    }


}
