package ua.com.foxminded.Universitycms.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.LectureRoom;
import ua.com.foxminded.Universitycms.repositories.LectureRoomRepository;
import ua.com.foxminded.Universitycms.services.LectureRoomService;

import java.util.List;

@Service
public class LectureRoomServiceImpl implements LectureRoomService {
    private final LectureRoomRepository lectureRoomRepository;

    public LectureRoomServiceImpl(LectureRoomRepository lectureRoomRepository) {
        this.lectureRoomRepository = lectureRoomRepository;
    }

    @Override
    @Transactional
    public void addOne(LectureRoom room) {
        lectureRoomRepository.save(room);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        lectureRoomRepository.deleteById(id);
    }

    @Override
    @Transactional
    public LectureRoom findById(Long id) {
        return lectureRoomRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void update(Long id, LectureRoom newRoom) {
        LectureRoom room = lectureRoomRepository.findById(id).orElseThrow();
        /*TODO set needed fields from newRoom*/
        lectureRoomRepository.save(room);
    }

    @Override
    public List<LectureRoom> getAll() {
        return lectureRoomRepository.findAll();
    }
}