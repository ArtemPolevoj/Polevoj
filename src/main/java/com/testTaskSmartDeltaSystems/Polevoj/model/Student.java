package com.testTaskSmartDeltaSystems.Polevoj.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "students")
@NoArgsConstructor
@AllArgsConstructor

public class Student {

    public Student(String lastName, String firstName, String middleName, String group, double averageGrade) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.group = group;
        this.averageGrade = averageGrade;
    }

    @Id
      private String id;

    private String lastName;
    private String firstName;
    private String middleName;
    private String group;
    private double averageGrade;
}
