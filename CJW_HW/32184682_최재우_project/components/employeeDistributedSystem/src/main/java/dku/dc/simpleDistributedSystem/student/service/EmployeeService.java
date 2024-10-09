package dku.dc.simpleDistributedSystem.student.service;

import dku.dc.simpleDistributedSystem.student.domain.Employee;
import dku.dc.simpleDistributedSystem.student.repository.EmployeeRepository;
import dku.dc.simpleDistributedSystem.student.service.request.EmployeeRequest;
import dku.dc.simpleDistributedSystem.student.service.request.UpdatingEmployeeRequest;
import dku.dc.simpleDistributedSystem.student.service.response.EmployeeResponse;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse save(final EmployeeRequest request) {
        final Employee employee = new Employee(request.getEmail(), request.getName());
        final Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeResponse(savedEmployee);
    }

    public EmployeeResponse get(final Long id) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow();

        return new EmployeeResponse(employee);
    }

    public EmployeeResponse change(final Long id, final UpdatingEmployeeRequest request) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow();

        employee.change(request.getEmail(), request.getName());
        final Employee updatedEmployee = employeeRepository.update(employee);
        return new EmployeeResponse(updatedEmployee);
    }

    public void remove(final Long id) {
        employeeRepository.remove(id);
    }
}
