package boss.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_seq")
    @SequenceGenerator(
            name = "task_seq",
            allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String taskName;
    private String taskText;

    @Column(name = "dead_line")
    private ZonedDateTime deadLine;

    @ManyToOne
    private Lesson lesson;

}