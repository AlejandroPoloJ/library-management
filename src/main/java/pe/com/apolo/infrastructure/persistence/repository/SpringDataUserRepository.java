package pe.com.apolo.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.apolo.infrastructure.persistence.entity.UserEntity;

import java.nio.channels.FileChannel;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);
}