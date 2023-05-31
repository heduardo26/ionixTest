package com.example.util.classes.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiMockResponse {
    private int responseCode;
    private String description;
    private Result result;
}
