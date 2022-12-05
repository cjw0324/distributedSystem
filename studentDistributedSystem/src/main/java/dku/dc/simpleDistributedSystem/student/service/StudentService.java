package dku.dc.simpleDistributedSystem.student.service;

import dku.dc.simpleDistributedSystem.student.domain.Student;
import dku.dc.simpleDistributedSystem.student.repository.StudentRepository;
import dku.dc.simpleDistributedSystem.student.service.request.StudentRequest;
import dku.dc.simpleDistributedSystem.student.service.request.UpdatingStudentRequest;
import dku.dc.simpleDistributedSystem.student.service.response.StudentResponse;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(final StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponse save(final StudentRequest request) {
        final Student student = new Student(request.getEmail(), request.getName());
        final Student savedStudent = studentRepository.save(student);
        return new StudentResponse(savedStudent);
    }

    public StudentResponse get(final Long id) {
        final Student student = studentRepository.findById(id)
                .orElseThrow();

        return new StudentResponse(student);
    }

    public StudentResponse change(final Long id, final UpdatingStudentRequest request) {
        final Student student = studentRepository.findById(id)
                .orElseThrow();

        student.change(request.getEmail(), request.getName());
        final Student updatedStudent = studentRepository.update(student);
        return new StudentResponse(updatedStudent);
    }

    public void remove(final Long id) {
        studentRepository.remove(id);
    }
}
