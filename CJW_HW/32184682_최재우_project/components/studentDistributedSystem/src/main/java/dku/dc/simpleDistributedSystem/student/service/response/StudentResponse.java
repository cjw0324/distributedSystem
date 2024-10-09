package dku.dc.simpleDistributedSystem.student.service.response;

import dku.dc.simpleDistributedSystem.student.domain.Student;

public class StudentResponse {

    private Long id;
    private String email;
    private String name;

    public StudentResponse() {
    }

    public StudentResponse(final Student student) {
        this(student.getId(), student.getEmail(), student.getName());
    }

    public StudentResponse(final Long id, final String email, final String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
