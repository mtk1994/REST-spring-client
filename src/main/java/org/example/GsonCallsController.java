package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Student;
import org.example.request.CreateStudentRequest;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GsonCallsController extends CallsController {

    @Override
    public String getStudentListAsString() throws Exception {
        URI getStudentsUri = new URI (BASE_STUDENT_LIST_URL);
        HttpRequest getStudentsRequest = HttpRequest.newBuilder()
                .uri(getStudentsUri)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send (getStudentsRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK )
            throw new Exception ("Unexpected response status code = " + response.statusCode());

        return response.body();
    }

    @Override
    public List<Student> getStudentList () throws Exception {
        URI getStudentsUri = new URI (BASE_STUDENT_LIST_URL);
        HttpRequest getStudentsRequest = HttpRequest.newBuilder()
                .uri(getStudentsUri)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send (getStudentsRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK )
            throw new Exception ("Unexpected response status code = " + response.statusCode());

       Gson gson = new Gson ();
       Type studentListType = new TypeToken<ArrayList<Student>>(){}.getType();

       return gson.fromJson (response.body(), studentListType);
    }

    @Override
    public Student getStudentByEmail (String studentEmail) throws Exception {
        //TODO figure out better way of handling GET parameters
        URI uri = new URI (GET_STUDENT_BY_EMAIL_URL + "?email=" + studentEmail);
        HttpRequest getStudentRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send (getStudentRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK )
            throw new Exception ("Unexpected response status code = " + response.statusCode());

        Gson gson = new Gson ();
        return gson.fromJson (response.body(), Student.class);
    }

    @Override
    public Student getStudentByEmailV2 (String studentEmail) throws Exception {
        //TODO figure out better way of handling GET parameters
        URI uri = new URI (GET_STUDENT_BY_EMAIL_V2_URL + "?email=" + studentEmail);
        HttpRequest getStudentRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpClient httpCLient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpCLient.send (getStudentRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK )
            throw new Exception ("Unexpected response status code = " + response.statusCode());

        Gson gson = new Gson ();
        // TODO handle when email is not find
        return gson.fromJson (response.body(), Student.class);
    }

    @Override
    public Student createStudent (Student student) throws Exception {
        URI uri = new URI (BASE_STUDENT_LIST_URL);
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Gson gson = new Gson ();

        CreateStudentRequest requestObj = new CreateStudentRequest (student.getName(), student.getEmail(), ft.format(student.getDateOfBirth()));
        String requestBody = gson.toJson (requestObj);

        HttpClient httpCLient = HttpClient.newHttpClient();
        HttpRequest createStudentRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpCLient.send (createStudentRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK )
            throw new Exception ("Unexpected response status code = " + response.statusCode());

        return gson.fromJson (response.body(), Student.class);
    }

    @Override
    public Student updateStudent (Long studentId, String name, String email) throws Exception {
        URI uri = new URI (BASE_STUDENT_LIST_URL + "/" + studentId + "?name=" + name + "&email=" + email);

        HttpRequest updateStudentRequest = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.noBody())
                .header("Content-Type", "application/json")
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send (updateStudentRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK )
            throw new Exception ("Unexpected response status code = " + response.statusCode());

        Gson gson = new Gson ();
        return gson.fromJson (response.body(), Student.class);
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

        Gson gson = new Gson ();
        return gson.fromJson (response.body(), Student.class);
    }

}
