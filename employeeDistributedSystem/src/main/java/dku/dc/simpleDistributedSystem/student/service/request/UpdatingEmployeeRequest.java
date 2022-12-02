package dku.dc.simpleDistributedSystem.student.service.request;

public class UpdatingEmployeeRequest {

    private String email;
    private String name;

    public UpdatingEmployeeRequest() {
    }

    public UpdatingEmployeeRequest(final String email, final String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
