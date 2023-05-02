package ua.com.foxminded.Universitycms.models;

import jakarta.persistence.*;
import lombok.*;
import ua.com.foxminded.Universitycms.models.enums.LectureNumber;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "subject_ref")
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "lecture_number", length = 32)
    private LectureNumber lectureNumber;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "teacher_ref")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "group_ref")
    private Group group;

    @OneToOne
    @JoinColumn(name = "room_ref")
    private LectureRoom lectureRoom;
}