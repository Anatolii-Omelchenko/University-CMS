package ua.com.foxminded.Universitycms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.Universitycms.models.Lecture;

import java.time.LocalDate;
import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByGroupIdAndDateBetweenOrderByDate(Long groupId, LocalDate start, LocalDate end);

    List<Lecture> findByTeacherIdAndDateBetweenOrderByDate(Long teacherId, LocalDate start, LocalDate end);

    List<Lecture> findAllByDateBetweenOrderByDate(LocalDate start, LocalDate end);
}