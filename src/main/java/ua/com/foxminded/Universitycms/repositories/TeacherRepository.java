package ua.com.foxminded.Universitycms.repositories;

import ua.com.foxminded.Universitycms.models.Teacher;

import java.util.Optional;

public interface TeacherRepository extends PersonRepository<Teacher> {
    Optional<Teacher> findByEmail(String email);
}