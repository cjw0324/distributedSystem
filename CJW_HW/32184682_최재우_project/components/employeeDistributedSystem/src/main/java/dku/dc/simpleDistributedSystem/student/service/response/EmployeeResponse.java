package dku.dc.simpleDistributedSystem.student.service.response;

import dku.dc.simpleDistributedSystem.student.domain.Employee;

public class EmployeeResponse {

    private Long id;
    private String email;
    private String name;

    public EmployeeResponse() {
    }

    public EmployeeResponse(final Employee employee) {
        this(employee.getId(), employee.getEmail(), employee.getName());
    }

    public EmployeeResponse(final Long id, final String email, final String name) {
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
