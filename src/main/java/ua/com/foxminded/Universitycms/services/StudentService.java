package ua.com.foxminded.Universitycms.services;

import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    @Transactional
    void addOne(Student student);

    @Transactional
    void deleteById(Long id);

    @Transactional
    Student findById(Long id);

    @Transactional
    void update(Long id, Student student);

    @Transactional
    List<Student> getAll();

    @Transactional
    Optional<Student> findByEmail(String email);
}