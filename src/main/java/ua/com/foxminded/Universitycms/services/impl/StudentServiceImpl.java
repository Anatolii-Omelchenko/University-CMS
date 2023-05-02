package ua.com.foxminded.Universitycms.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Student;
import ua.com.foxminded.Universitycms.repositories.PersonRepository;
import ua.com.foxminded.Universitycms.services.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final PasswordEncoder encoder;
    private final PersonRepository<Student> personRepository;

    public StudentServiceImpl(PasswordEncoder encoder, PersonRepository<Student> personRepository) {
        this.encoder = encoder;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public void addOne(Student student) {
        String encodePassword = encoder.encode(student.getPassword());
        student.setPassword(encodePassword);
        personRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Student findById(Long id) {
        return personRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void update(Long id, Student newStudent) {
        Student student = personRepository.findById(id).orElseThrow();
        String encodePassword = encoder.encode(newStudent.getPassword());

        student.setEmail(newStudent.getEmail());
        student.setName(newStudent.getName());
        student.setPassword(encodePassword);
        student.setBirthdate(newStudent.getBirthdate());
        student.setGender(newStudent.getGender());
        student.setPhone(newStudent.getPhone());
        student.setRoles(newStudent.getRoles());
        student.setPaymentForm(newStudent.getPaymentForm());
        student.setGroup(newStudent.getGroup());

        personRepository.save(student);
    }

    @Override
    @Transactional
    public List<Student> getAll() {
        return personRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Student> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}