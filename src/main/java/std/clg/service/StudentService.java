package std.clg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import std.clg.entity.Student;
import std.clg.exception.DuplicateEmailException;
import std.clg.exception.ResourceNotFoundException;
import std.clg.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student create(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new DuplicateEmailException("A student with this email already exists");
        }
        return studentRepository.save(student);
    }

    public Student update(Long id, Student student) {
        Student existingStudent = findById(id);
        if (studentRepository.existsByEmailAndIdNot(student.getEmail(), id)) {
            throw new DuplicateEmailException("A student with this email already exists");
        }

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setDepartment(student.getDepartment());
        existingStudent.setYear(student.getYear());
        existingStudent.setPhone(student.getPhone());

        return studentRepository.save(existingStudent);
    }

    public void delete(Long id) {
        Student student = findById(id);
        studentRepository.delete(student);
    }
}
