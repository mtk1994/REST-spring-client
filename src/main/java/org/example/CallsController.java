package org.example;

import org.example.model.Student;

import java.util.List;

public abstract class CallsController {

    protected static final String STUDENT_SERVICE_URL = "http://localhost:8081/api/v1/";

    protected static final String BASE_STUDENT_LIST_URL = STUDENT_SERVICE_URL + "student";
    protected static final String GET_STUDENT_BY_EMAIL_URL = STUDENT_SERVICE_URL + "student/getByEmail";

    protected static final String GET_STUDENT_BY_EMAIL_V2_URL = STUDENT_SERVICE_URL + "student/getByEmailV2";

    public abstract String getStudentListAsString () throws Exception;

   public abstract List<Student> getStudentList () throws Exception;

    public abstract Student getStudentByEmail (String studentEmail) throws Exception;

    public abstract Student getStudentByEmailV2 (String studentEmail) throws Exception;

    public abstract Student updateStudent (Long studentId, String name, String email) throws Exception;

    public abstract Student createStudent (Student student) throws Exception;

    public abstract Student deleteStudent (Long studentId) throws Exception;

}
