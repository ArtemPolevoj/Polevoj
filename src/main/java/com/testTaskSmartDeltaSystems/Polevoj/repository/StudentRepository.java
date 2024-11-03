package com.testTaskSmartDeltaSystems.Polevoj.repository;

import com.testTaskSmartDeltaSystems.Polevoj.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String>{
}
