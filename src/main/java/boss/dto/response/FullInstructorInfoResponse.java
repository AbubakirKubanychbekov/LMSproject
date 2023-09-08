package boss.dto.response;

public class FullInstructorInfoResponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private String group;
    private int numberOfStudents;

    public FullInstructorInfoResponse(Long id, String name, String phoneNumber, String group, int numberOfStudents) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.group = group;
        this.numberOfStudents = numberOfStudents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
