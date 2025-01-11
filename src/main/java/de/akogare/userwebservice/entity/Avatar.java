package de.akogare.userwebservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * Represents an avatar image for a user.
 */
@Entity
@Table(name = "avatars")
public class Avatar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID   id;
	@Column(unique = true, nullable = false)
	private String keycloakId;
	@Lob
	@Column(name = "image", columnDefinition = "oid")
	private byte[] image;

	public Avatar() {
	}

	public Avatar(String keycloakId, byte[] image) {
		this.keycloakId = keycloakId;
		this.image = image;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getKeycloakId() {
		return keycloakId;
	}

	public void setKeycloakId(String keycloakId) {
		this.keycloakId = keycloakId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Avatar avatar)) return false;
		return Objects.equals(id, avatar.id) && Objects.equals(keycloakId, avatar.keycloakId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, keycloakId);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Avatar.class.getSimpleName() + "[", "]").add("id=" + id)
				.add("keycloakId='" + keycloakId + "'")
				.add("image=" + Arrays.toString(image))
				.toString();
	}
}
