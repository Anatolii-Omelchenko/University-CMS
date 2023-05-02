package ua.com.foxminded.Universitycms.services;

import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Lecture;

import java.time.LocalDate;
import java.util.List;

public interface LectureService {
    @Transactional
    void addOne(Lecture lecture);

    @Transactional
    void deleteById(Long id);

    @Transactional
    Lecture findById(Long id);

    @Transactional
    void update(Long id, Lecture lecture);

    @Transactional
    List<Lecture> getAll();

    @Transactional
    List<Lecture> findByGroupIdAndDate(Long groupId, LocalDate start, LocalDate end);

    @Transactional
    List<Lecture> findByTeacherIdAndDate(Long teacherId, LocalDate start, LocalDate end);

    @Transactional
    List<Lecture> findAllByDate(LocalDate start, LocalDate end);
}