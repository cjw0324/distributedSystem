package dku.dc.simpleDistributedSystem.student.controller;

import dku.dc.simpleDistributedSystem.student.service.EmployeeService;
import dku.dc.simpleDistributedSystem.student.service.request.EmployeeRequest;
import dku.dc.simpleDistributedSystem.student.service.request.UpdatingEmployeeRequest;
import dku.dc.simpleDistributedSystem.student.service.response.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getStudent(@PathVariable("id") Long id) {
        final EmployeeResponse response = employeeService.get(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> save(@RequestBody EmployeeRequest request) {
        final EmployeeResponse response = employeeService.save(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateStudent(@PathVariable("id") Long id,
                                                          @RequestBody UpdatingEmployeeRequest request) {
        final EmployeeResponse response = employeeService.change(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmail(@PathVariable("id") Long id) {
        employeeService.remove(id);
        return ResponseEntity.ok().build();
    }
}
