package boss.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lesson_seq")
    @SequenceGenerator(
            name = "lesson_seq",
            allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String lessonName;

    @OneToMany(mappedBy = "lesson")
    private List<Task>tasks;

    @ManyToOne
    private Course course;

}