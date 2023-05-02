package ua.com.foxminded.Universitycms.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Group;
import ua.com.foxminded.Universitycms.models.Student;
import ua.com.foxminded.Universitycms.repositories.GroupRepository;
import ua.com.foxminded.Universitycms.repositories.PersonRepository;
import ua.com.foxminded.Universitycms.services.GroupService;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final PersonRepository<Student> personRepository;

    public GroupServiceImpl(GroupRepository groupRepository, PersonRepository<Student> personRepository) {
        this.groupRepository = groupRepository;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public void addOne(Group group) {
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void addMany(Long count) {
        for (int i = 0; i < count; i++) {
            String groupName = String.format("GR-%02d", i);
            groupRepository.save(new Group(groupName));
        }
    }

    @Override
    @Transactional
    public void addStudent(Long studentId, Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        Student student = personRepository.findById(studentId).orElseThrow();
        group.addStudent(student);
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void deleteById(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    @Transactional
    public void removeAllStudents(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        group.getStudents().clear();
        groupRepository.save(group);
    }

    @Override
    public void update(Long groupId, Group newGroup) {
        Group group = groupRepository.findById(groupId).orElseThrow();

        group.setGroupName(newGroup.getGroupName());
        group.setEducationForm(newGroup.getEducationForm());

        groupRepository.save(group);
    }

    @Override
    @Transactional
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @Override
    @Transactional
    public Group getOneGroup(Long id) {
        return groupRepository.findById(id).orElseThrow();
    }

    @Override
    public Optional<Group> getGroupByName(String name) {
        return groupRepository.findByGroupName(name);
    }
}