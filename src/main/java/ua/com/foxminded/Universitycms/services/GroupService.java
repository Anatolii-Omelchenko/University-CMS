package ua.com.foxminded.Universitycms.services;

import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    @Transactional
    void addOne(Group group);

    @Transactional
    void addMany(Long count);

    @Transactional
    void addStudent(Long studentId, Long groupId);

    @Transactional
    void deleteById(Long groupId);

    @Transactional
    void removeAllStudents(Long groupId);

    @Transactional
    void update(Long groupId, Group group);

    @Transactional
    List<Group> getAll();

    @Transactional
    Group getOneGroup(Long id);

    @Transactional
    Optional<Group> getGroupByName(String name);
}