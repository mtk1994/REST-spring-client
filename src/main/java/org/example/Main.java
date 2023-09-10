package org.example;

import org.example.model.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // test GET methods
            String correctStudentEmail = "maria.m@gmail.com";

            // test gson
            GsonCallsController gson = new GsonCallsController();

            String gsonStudentsAsText = gson.getStudentListAsString();
            List<Student> gsonStudentsAsList = gson.getStudentList();
            Student gsonStudentByEmail = gson.getStudentByEmail(correctStudentEmail);
            Student gsonStudentByEmailV2 = gson.getStudentByEmailV2(correctStudentEmail);

            // test jackson
            JacksonCallsController jackson = new JacksonCallsController();

            String jacksonStudentsAsText = jackson.getStudentListAsString();
            List<Student> jacksonStudentsAsList = jackson.getStudentList();
            Student jacksonStudentByEmail = jackson.getStudentByEmail(correctStudentEmail);
            Student jacksonStudentByEmailV2 = jackson.getStudentByEmailV2(correctStudentEmail);

            // test POST/PUT/DELETE methods
            Student testStudent = createTestStudent();
            String newName = "NEW_name";
            String newEmail = "new@email.com";

            // test gson
            Student gsonCreatedStudent = gson.createStudent(testStudent);
            Student gsonUpdatedStudent = gson.updateStudent(gsonCreatedStudent.getId(), newName, newEmail);
            Student gsonDeletedStudent = gson.deleteStudent(gsonUpdatedStudent.getId());

            // test jackson
            Student jacksonCreatedStudent = jackson.createStudent(testStudent);
            Student jacksonUpdatedStudent = jackson.updateStudent(jacksonCreatedStudent.getId(), newName, newEmail);
            Student jacksonDeletedStudent = jackson.deleteStudent(jacksonUpdatedStudent.getId());

            System.out.println("Hello world!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static Student createTestStudent (){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String studentBirthDateStr = "2000-01-01";
        Date studentBirthDate;
        try {
            studentBirthDate = ft.parse(studentBirthDateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Unparseable using " + ft);
        }
        return new Student("Test student", "test@student.com", studentBirthDate);
    }
}