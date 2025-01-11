package de.akogare.userwebservice.repositories;

import de.akogare.userwebservice.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link Avatar} entities.
 *
 * @author Samuel Abramov
 */
public interface AvatarRepository extends JpaRepository<Avatar, UUID> {

	Optional<Avatar> findByKeycloakId(String keycloakId);

}
