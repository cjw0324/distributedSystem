package dku.dc.simpleDistributedSystem.student.repository;

import dku.dc.simpleDistributedSystem.student.domain.Student;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class StudentRepository {

    private static final AtomicLong id = new AtomicLong(0);
    private static final Map<Long, Student> database = new ConcurrentHashMap<>();

    static {
        final long id = StudentRepository.id.incrementAndGet();
        final Student student = new Student(id, "sample@gmail.com", "sample");
        database.put(id, student);
    }

    public Student save(final Student student) {
        final long incrementId = id.incrementAndGet();
        final Student preparedStudent = new Student(incrementId, student.getEmail(), student.getName());
        database.put(incrementId, preparedStudent);
        return database.get(incrementId);
    }

    public Student update(final Student student) {
        database.put(student.getId(), student);
        return database.get(student.getId());
    }

    public Optional<Student> findById(final Long id) {
        return Optional.ofNullable(database.get(id));
    }

    public void remove(final Long id) {
        database.remove(id);
    }
}
