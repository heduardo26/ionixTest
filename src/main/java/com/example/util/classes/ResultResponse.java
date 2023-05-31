package com.example.util.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {

    private int responseCode;
    private String description;
    private long elapsedTime;
    private Result result;
}
