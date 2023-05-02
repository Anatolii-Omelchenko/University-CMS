package ua.com.foxminded.Universitycms.services;

import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    @Transactional
    void addOne(Teacher teacher);

    @Transactional
    void deleteById(Long id);

    @Transactional
    Teacher findById(Long id);

    @Transactional
    void update(Long id, Teacher teacher);

    @Transactional
    List<Teacher> getAll();

    @Transactional
    Optional<Teacher> findByEmail(String email);
}