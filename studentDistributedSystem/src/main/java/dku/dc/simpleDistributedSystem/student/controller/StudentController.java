package dku.dc.simpleDistributedSystem.student.controller;

import dku.dc.simpleDistributedSystem.student.service.StudentService;
import dku.dc.simpleDistributedSystem.student.service.request.StudentRequest;
import dku.dc.simpleDistributedSystem.student.service.request.UpdatingStudentRequest;
import dku.dc.simpleDistributedSystem.student.service.response.StudentResponse;
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
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable("id") Long id) {
        final StudentResponse response = studentService.get(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<StudentResponse> save(@RequestBody StudentRequest request) {
        final StudentResponse response = studentService.save(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable("id") Long id,
                                                         @RequestBody UpdatingStudentRequest request) {
        final StudentResponse response = studentService.change(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponse> updateEmail(@PathVariable("id") Long id) {
        studentService.remove(id);
        return ResponseEntity.ok().build();
    }
}
