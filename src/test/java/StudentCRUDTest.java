
import org.example.CallsController;
import org.example.GsonCallsController;
import org.example.JacksonCallsController;
import org.example.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test basic CRUD (create, read, update and delete) operations using REST API and using gson and Jackson to map json.
 */
public class StudentCRUDTest {

    private static Student testStudent;
    private static Student tempStudent;

    // Executed once before all test methods in this class
    @BeforeAll
    static void beforeAll () {
        testStudent = createTestStudent ();
    }

    // Executed after each test method in this class
    @AfterEach
    void afterEach () {
        CallsController cc = new JacksonCallsController ();
        try {
            cc.deleteStudent (tempStudent.getId());
        } catch (Exception e) {
            // if exception has been thrown, that's means we already cleared temporary objects properly in the test
            // we don't need to do anything here
        }
    }

    /**
     * Test gson create, read, update and delete operations using REST API.
     * @throws Exception
     */
    @Test
    void TestCRUDGson () throws Exception {
        TestCRUD (new GsonCallsController ());
    }

    /**
     * Test Jackson create, read, update and delete operations using REST API.
     * @throws Exception
     */
    @Test
    void TestCRUDJackson () throws Exception {
        TestCRUD (new JacksonCallsController ());
    }

    void TestCRUD (CallsController cc) throws Exception {
        List<Student> studentListBeforeCreate = cc.getStudentList ();

        // student shouldn't exist yet
        assertFalse (studentListBeforeCreate.contains (testStudent));

        Exception studentExistBeforeCreateException = assertThrows (Exception.class, () -> cc.getStudentByEmail (testStudent.getEmail()));

        String expectedMessage = "Unexpected response status code = 500";
        String actualMessage = studentExistBeforeCreateException.getMessage();
        // student shouldn't exist yet
        assertTrue (actualMessage.contains(expectedMessage));

        // create student
        tempStudent = cc.createStudent (testStudent);

        List<Student> studentListAfterCreate = cc.getStudentList ();
        // student should exist now
        assertTrue (studentListAfterCreate.contains (tempStudent));

        tempStudent = cc.getStudentByEmail (tempStudent.getEmail());
        // student should exist now
        assertNotNull (tempStudent);

        String newStudentName = "NEW_name";
        String newStudentEmail = "new@email.com";

        tempStudent = cc.updateStudent (tempStudent.getId(), newStudentName, newStudentEmail);
        tempStudent = cc.getStudentByEmail (newStudentEmail);

        assertEquals (tempStudent.getName(), newStudentName);
        assertEquals (tempStudent.getEmail(), newStudentEmail);

        tempStudent = cc.deleteStudent (tempStudent.getId());

        List<Student> studentListAfterDelete = cc.getStudentList ();

        // student shouldn't exist now
        assertFalse (studentListAfterDelete.contains (tempStudent));

        Exception studentExistAfterDeleteException = assertThrows (Exception.class, () -> cc.getStudentByEmail (tempStudent.getEmail()));

        String actualMessageAfterDelete = studentExistAfterDeleteException.getMessage();
        // student shouldn't exist now
        assertTrue (actualMessageAfterDelete.contains(expectedMessage));
    }

    // Utils
    private static Student createTestStudent (){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String studentBirthDateStr = "2000-01-01";
        Date studentBirthDate;
        try {
            studentBirthDate = ft.parse(studentBirthDateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Unparsable using " + ft);
        }
        return new Student("Test student", "test@student.com", studentBirthDate);
    }
}
