package ua.com.foxminded.Universitycms.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "lecture_rooms")
public class LectureRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 5, name = "room_name")
    private String roomName;

    @Column(nullable = false)
    private Integer capacity;
}