package com.learn.different_db_fetches.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserNameDto {
    private String firstName;
    private String lastName;

    public UserNameDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}