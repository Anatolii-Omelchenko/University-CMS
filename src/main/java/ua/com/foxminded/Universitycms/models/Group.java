package ua.com.foxminded.Universitycms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ua.com.foxminded.Universitycms.models.enums.EducationForm;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @NotBlank(message = "Group name cant be empty!")
    @Size(min = 5, max = 5, message = "Size should be 5!")
    @Column(nullable = false, unique = true, length = 5, name = "group_name")
    private String groupName;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<Lecture> schedule = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "education_form", length = 32)
    private EducationForm educationForm;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    @PreRemove
    private void preRemove() {
        for (Student s : students) {
            s.setGroup(null);
        }
        for (Lecture l : schedule) {
            l.setGroup(null);
        }
    }

    public void addStudent(Student student) {
        students.add(student);
    }
}