package ua.com.foxminded.Universitycms.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Lecture;
import ua.com.foxminded.Universitycms.repositories.LectureRepository;
import ua.com.foxminded.Universitycms.services.LectureService;

import java.time.LocalDate;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    public LectureServiceImpl(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    @Transactional
    public void addOne(Lecture lecture) {
        lectureRepository.save(lecture);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        lectureRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Lecture findById(Long id) {
        return lectureRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void update(Long id, Lecture newLecture) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow();

        lecture.setTeacher(newLecture.getTeacher());
        lecture.setGroup(newLecture.getGroup());
        lecture.setLectureRoom(newLecture.getLectureRoom());
        lecture.setLectureNumber(newLecture.getLectureNumber());
        lecture.setDate(newLecture.getDate());
        lecture.setSubject(newLecture.getSubject());

        lectureRepository.save(lecture);
    }

    @Override
    @Transactional
    public List<Lecture> getAll() {
        return lectureRepository.findAll();
    }

    @Override
    @Transactional
    public List<Lecture> findByGroupIdAndDate(Long groupId, LocalDate start, LocalDate end) {
        return lectureRepository.findByGroupIdAndDateBetweenOrderByDate(groupId, start, end);
    }

    @Override
    @Transactional
    public List<Lecture> findByTeacherIdAndDate(Long teacherId, LocalDate start, LocalDate end) {
        return lectureRepository.findByTeacherIdAndDateBetweenOrderByDate(teacherId, start, end);
    }

    @Override
    public List<Lecture> findAllByDate(LocalDate start, LocalDate end) {
        return lectureRepository.findAllByDateBetweenOrderByDate(start, end);
    }

}