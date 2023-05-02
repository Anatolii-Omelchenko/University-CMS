package ua.com.foxminded.Universitycms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.Universitycms.models.LectureRoom;

public interface LectureRoomRepository extends JpaRepository<LectureRoom, Long> {
}