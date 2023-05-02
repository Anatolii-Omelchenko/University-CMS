package ua.com.foxminded.Universitycms.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.Universitycms.models.Subject;
import ua.com.foxminded.Universitycms.repositories.SubjectRepository;
import ua.com.foxminded.Universitycms.services.SubjectService;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    @Transactional
    public void addOne(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Subject findById(Long id) {
        return subjectRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void update(Long id, Subject newSubject) {
        Subject subject = subjectRepository.findById(id).orElseThrow();

        subject.setSubjectName(newSubject.getSubjectName());
        subject.setDescription(newSubject.getDescription());

        subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Optional<Subject> findByName(String name) {
        return subjectRepository.findBySubjectName(name);
    }
}