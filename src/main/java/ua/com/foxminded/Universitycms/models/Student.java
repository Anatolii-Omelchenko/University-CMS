package ua.com.foxminded.Universitycms.models;

import jakarta.persistence.*;

import lombok.*;
import ua.com.foxminded.Universitycms.models.enums.Gender;
import ua.com.foxminded.Universitycms.models.enums.PaymentForm;
import ua.com.foxminded.Universitycms.models.enums.Role;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("STD")
public class Student extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_form", length = 32)
    private PaymentForm paymentForm;

    @ManyToOne
    @JoinColumn(name = "group_ref")
    private Group group;

    public Student(Long id, String name) {
        super(id, name);
    }

    public Student(Long id, String name, Set<Role> roles) {
        super(id, name, roles);
    }

    public Student(Long id, String name, Gender gender, LocalDate birthdate, String phone, String email, String password, Set<Role> roles, PaymentForm paymentForm) {
        super(id, name, gender, birthdate, phone, email, password, roles);
        this.paymentForm = paymentForm;
    }
}