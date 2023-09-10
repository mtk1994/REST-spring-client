package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Student;
import org.example.request.CreateStudentRequest;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.List;

public class JacksonCallsController extends CallsController {

    @Override
    public String getStudentListAsString () throws Exception {
        return getStudentList().toString();
    }

    @Override
    public List<Student> getStudentList() throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_STUDENT_LIST_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new Exception("Unexpected response status code = " + conn.getResponseCode() + " " + conn.getResponseMessage());

            ObjectMapper mapper = new ObjectMapper();
            try (InputStream is = conn.getInputStream()) {
                return mapper.readValue (is, new TypeReference<>() {});
            }
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    @Override
    public Student getStudentByEmail(String studentEmail) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(GET_STUDENT_BY_EMAIL_URL + "?email=" + studentEmail);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new Exception("Unexpected response status code = " + conn.getResponseCode() + " " + conn.getResponseMessage());

            ObjectMapper mapper = new ObjectMapper();
            try (InputStream is = conn.getInputStream()) {
                return mapper.readValue(is, Student.class);
            }
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    @Override
    public Student getStudentByEmailV2(String studentEmail) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(GET_STUDENT_BY_EMAIL_V2_URL + "?email=" + studentEmail);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new Exception("Unexpected response status code = " + conn.getResponseCode() + " " + conn.getResponseMessage());

            ObjectMapper mapper = new ObjectMapper();
            try (InputStream is = conn.getInputStream()) {
                return mapper.readValue(is, Student.class);
            }
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    @Override
    public Student createStudent(Student student) throws Exception {
        URI uri = new URI (BASE_STUDENT_LIST_URL);
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

        ObjectMapper objectMapper = new ObjectMapper();
        CreateStudentRequest requestObj = new CreateStudentRequest (student.getName(), student.getEmail(), ft.format(student.getDateOfBirth()));
        String requestBody = objectMapper.writeValueAsString (requestObj);

        HttpClient httpCLient = HttpClient.newHttpClient();
        HttpRequest createStudentRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpCLient.send (createStudentRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK )
            throw new Exception ("Unexpected response status code = " + response.statusCode());

        return objectMapper.readValue(response.body(), Student.class);
    }

    @Override
    public Student updateStudent(Long studentId, String name, String email) throws Exception {
        URI uri = new URI (BASE_STUDENT_LIST_URL + "/" + studentId + "?name=" + name + "&email=" + email);

        HttpRequest updateStudentRequest = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.noBody())
                .header("Content-Type", "application/json")
                .build();

        HttpClient httpCLient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpCLient.send (updateStudentRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK )
            throw new Exception ("Unexpected response status code = " + response.statusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Student.class);
    }

    @Override
    public Student deleteStudent(Long studentId) throws Exception {
        URI uri = new URI (BASE_STUDENT_LIST_URL + "/" + studentId);

        HttpRequest deleteStudentRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE ()
                .header("Content-Type", "application/json")
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send (deleteStudentRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK )
            throw new Exception ("Unexpected response status code = " + response.statusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Student.class);
    }
}
