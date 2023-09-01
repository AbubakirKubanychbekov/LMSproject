package boss.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_seq")
    @SequenceGenerator(
            name = "course_seq",
    allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String courseName;

    private LocalDate dateOfStart;

    private String description;

    @OneToMany
    private List<Lesson>lessons;

    @ManyToMany
    private List<Group>groups;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Instructor instructor;


    public void addGroup(Group group) {
        if (groups == null){
            groups=new ArrayList<>();
        } else {
            groups.add(group);
        }
    }
}