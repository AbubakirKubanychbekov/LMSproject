package boss.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "instructor_seq")
    @SequenceGenerator(
            name = "instructor_seq",
            allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstName;
    private String lastName;

    @Valid
    private String phoneNumber;
    private String specialization;

    @ManyToMany(mappedBy = "instructors",
    cascade = CascadeType.ALL)
    private List<Company> companies = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "instructor",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST})
    private List<Course> courses;



    public void addCompany(Company company) {
        if (companies == null) {
            companies = new ArrayList<>();
        } else {
            companies.add(company);
        }
    }
}