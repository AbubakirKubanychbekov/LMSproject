package boss.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_seq")
    @SequenceGenerator(
            name = "student_seq",
            allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String studyFormat;

    private boolean isBlock;

    @ManyToOne
    private Group group;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;






}