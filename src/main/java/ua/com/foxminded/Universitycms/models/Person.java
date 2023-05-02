package ua.com.foxminded.Universitycms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;
import ua.com.foxminded.Universitycms.models.enums.Gender;
import ua.com.foxminded.Universitycms.models.enums.Role;

import java.time.LocalDate;
import java.util.Set;

@Component
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "P_TYPE")
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @NotBlank(message = "Name cant be empty")
    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Gender gender;

    @NotNull(message = "Birthdate cant be empty!")
    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(length = 32, unique = true)
    private String phone;

    @Column(length = 100, unique = true)
    @NotBlank(message = "Email cant be empty")
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password cant be empty")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "person_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = Set.of(Role.USER);

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(Long id, String name, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }
}