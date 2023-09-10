package org.example.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateStudentRequest(@JsonProperty("name") String name,
                                   @JsonProperty("email") String email,
                                   @JsonProperty("dateOfBirth") String dateOfBirth) {
}
