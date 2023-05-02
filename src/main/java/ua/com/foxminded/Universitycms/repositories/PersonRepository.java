package ua.com.foxminded.Universitycms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.Universitycms.models.Person;

import java.util.Optional;

public interface PersonRepository<T extends Person> extends JpaRepository<T, Long> {
    Optional<T> findByEmail(String email);
}