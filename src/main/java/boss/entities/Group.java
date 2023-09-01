package boss.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_seq")
    @SequenceGenerator(
            name = "group_seq",
            allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String groupName;
    private String imageLink;
    private String description;

    @ManyToMany(mappedBy = "groups")
    private List<Course>courses;

    @OneToMany(mappedBy = "group",
    cascade = CascadeType.ALL)
    private List<Student>students;

    public void addCourse(Course course) {
        if (courses == null){
            courses=new ArrayList<>();
        }else {
            courses.add(course);
        }
    }
}