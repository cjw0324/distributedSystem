package dku.dc.simpleDistributedSystem.student.domain;

public class Student {

    private Long id;
    private String email;
    private String name;

    public Student(final String email, final String name) {
        this.email = email;
        this.name = name;
    }

    public Student(final Long id, final String email, final String name) {
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

    public void change(final String email, final String name) {
        if (email != null) {
            this.email = email;
        }
        if (name != null) {
            this.name = name;
        }
    }
}
