package gateway.request;

public class RequestDto {

    private String email;
    private String name;

    public RequestDto() {
    }

    public RequestDto(final String email, final String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "StudentRequest{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
