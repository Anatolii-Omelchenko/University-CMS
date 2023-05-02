package ua.com.foxminded.Universitycms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.Universitycms.models.Subject;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findBySubjectName(String subjectName);
}