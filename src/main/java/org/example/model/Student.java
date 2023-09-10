package org.example.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("dateOfBirth")
    private Date dateOfBirth;

    @JsonProperty("age")
    private Integer age;

    public Student() {
    }

    public Student(Long id, String name, String email, Date birth, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = birth;
        this.age = age;
    }

    public Student(Long id, String name, String email, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = birth;
    }

    public Student(String name, String email, Date birth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = birth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth (Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

   /* public Integer getAge() {
        //return age;
        return Period.between (this.dateOfBirth, LocalDate.now()).getYears();
    }*/

   /* public void setAge(Integer age) {
        this.age = age;
    }*/

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                '}';
    }
}