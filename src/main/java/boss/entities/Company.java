package boss.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;



@EntityListeners(Listens.class)
@Entity
@Table(name = "companies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Company {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_seq")
    @SequenceGenerator(
            name = "company_seq",
            allocationSize = 1)
    @Column(
            name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    private String country;
    private String address;
    private String phoneNumber;


    @OneToMany(mappedBy = "company")
    private List<Course>courses;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Instructor>instructors;

    private LocalDate createdEt;
    private LocalDate updatedEt;

}