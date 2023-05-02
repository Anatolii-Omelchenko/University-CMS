package ua.com.foxminded.Universitycms.models;

import jakarta.persistence.*;
import lombok.*;
import ua.com.foxminded.Universitycms.models.enums.AcademicDegree;
import ua.com.foxminded.Universitycms.models.enums.Gender;
import ua.com.foxminded.Universitycms.models.enums.Role;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@DiscriminatorValue("TCH")
public class Teacher extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_degree", length = 32)
    private AcademicDegree academicDegree = AcademicDegree.TEACHER;

    @Column(columnDefinition = "integer default 0")
    private Integer experience = 0;

    @ToString.Exclude
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Set<Lecture> schedule;

    public Teacher(Long id, String name) {
        super(id, name);
    }

    public Teacher(Long id, String name, Set<Role> roles) {
        super(id, name, roles);
    }

    public Teacher(Long id, String name, Gender gender, LocalDate birthdate, String phone, String email, String password, Set<Role> roles, AcademicDegree academicDegree, Integer experience) {
        super(id, name, gender, birthdate, phone, email, password, roles);
        this.academicDegree = academicDegree;
        this.experience = experience;
    }

    @PreRemove
    private void preRemove() {
        for (Lecture l : schedule) {
            l.setTeacher(null);
        }
    }
}