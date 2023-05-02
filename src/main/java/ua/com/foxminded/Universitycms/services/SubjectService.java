package ua.com.foxminded.Universitycms.services;

import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    @Transactional
    void addOne(Subject subject);

    @Transactional
    void deleteById(Long id);

    @Transactional
    Subject findById(Long id);

    @Transactional
    void update(Long id, Subject subject);

    @Transactional
    List<Subject> getAll();

    @Transactional
    Optional<Subject> findByName(String name);
}