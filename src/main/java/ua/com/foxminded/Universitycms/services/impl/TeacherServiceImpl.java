package ua.com.foxminded.Universitycms.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Teacher;
import ua.com.foxminded.Universitycms.repositories.PersonRepository;
import ua.com.foxminded.Universitycms.services.TeacherService;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final PersonRepository<Teacher> personRepository;
    private final PasswordEncoder encoder;

    public TeacherServiceImpl(PersonRepository<Teacher> personRepository, PasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void addOne(Teacher teacher) {
        String encodePassword = encoder.encode(teacher.getPassword());
        teacher.setPassword(encodePassword);
        personRepository.save(teacher);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Teacher findById(Long id) {
        return personRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void update(Long id, Teacher newTeacher) {
        Teacher teacher = personRepository.findById(id).orElseThrow();
        String encodePassword = encoder.encode(newTeacher.getPassword());

        teacher.setEmail(newTeacher.getEmail());
        teacher.setName(newTeacher.getName());
        teacher.setPassword(encodePassword);
        teacher.setBirthdate(newTeacher.getBirthdate());
        teacher.setGender(newTeacher.getGender());
        teacher.setPhone(newTeacher.getPhone());
        teacher.setRoles(newTeacher.getRoles());
        teacher.setExperience(newTeacher.getExperience());
        teacher.setAcademicDegree(newTeacher.getAcademicDegree());

        personRepository.save(teacher);
    }

    @Override
    @Transactional
    public List<Teacher> getAll() {
        return personRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Teacher> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}