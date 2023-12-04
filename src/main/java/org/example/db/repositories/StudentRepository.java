package org.example.db.repositories;

import org.example.db.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);
}