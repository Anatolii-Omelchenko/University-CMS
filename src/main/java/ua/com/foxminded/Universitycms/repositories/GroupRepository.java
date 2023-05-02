package ua.com.foxminded.Universitycms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.Universitycms.models.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupName(String groupName);
}