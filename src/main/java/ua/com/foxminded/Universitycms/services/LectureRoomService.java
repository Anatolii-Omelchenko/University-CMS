package ua.com.foxminded.Universitycms.services;

import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.LectureRoom;

import java.util.List;

public interface LectureRoomService {
    @Transactional
    void addOne(LectureRoom room);

    @Transactional
    void deleteById(Long id);

    @Transactional
    LectureRoom findById(Long id);

    @Transactional
    void update(Long id, LectureRoom room);

    @Transactional
    List<LectureRoom> getAll();
}