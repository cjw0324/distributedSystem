package dku.dc.simpleDistributedSystem.student.repository;

import dku.dc.simpleDistributedSystem.student.domain.Employee;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class EmployeeRepository {

    private static final AtomicLong id = new AtomicLong(0);
    private static final Map<Long, Employee> database = new ConcurrentHashMap<>();

    static {
        final long id = EmployeeRepository.id.incrementAndGet();
        final Employee employee = new Employee(id, "sample@gmail.com", "sample");
        database.put(id, employee);
    }

    public Employee save(final Employee employee) {
        final long incrementId = id.incrementAndGet();
        final Employee preparedEmployee = new Employee(incrementId, employee.getEmail(), employee.getName());
        database.put(incrementId, preparedEmployee);
        return database.get(incrementId);
    }

    public Employee update(final Employee employee) {
        database.put(employee.getId(), employee);
        return database.get(employee.getId());
    }

    public Optional<Employee> findById(final Long id) {
        return Optional.ofNullable(database.get(id));
    }

    public void remove(final Long id) {
        database.remove(id);
    }
}
