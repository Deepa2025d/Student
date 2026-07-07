package std.clg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import std.clg.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
