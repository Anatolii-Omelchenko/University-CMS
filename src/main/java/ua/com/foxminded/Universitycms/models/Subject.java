package ua.com.foxminded.Universitycms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @NotBlank(message = "Subject name cant be empty!")
    @Column(nullable = false, unique = true, length = 64, name = "subject_name")
    private String subjectName;

    private String description;

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public Subject(Long id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }
}