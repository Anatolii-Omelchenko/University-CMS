package ua.com.foxminded.Universitycms.repositories;

import ua.com.foxminded.Universitycms.models.Student;

import java.util.Optional;

public interface StudentRepository extends PersonRepository<Student> {
    Optional<Student> findByEmail(String email);
}