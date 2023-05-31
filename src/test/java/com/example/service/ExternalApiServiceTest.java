package com.example.service;

import com.example.util.classes.ResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalApiServiceTest {

    private ExternalApiService externalApiServiceUnderTest;

    @BeforeEach
    void setUp() {
        externalApiServiceUnderTest = new ExternalApiService();
    }

    @Test
    void testGetExternalService() {
        // Setup
        String param = "1-9";
        // Run the test
        final ResponseEntity<Object> result = externalApiServiceUnderTest.getExternalService(param);
        ResultResponse resultResponse = (ResultResponse) result.getBody();

        // Verify the results
        assertThat(resultResponse.getResponseCode()).isEqualTo(200);
        assertThat(resultResponse.getDescription()).isEqualTo("OK");
        assertThat(resultResponse.getResult().getRegisterCount()).isEqualTo(3);
        //assert
        //System.out.println(resultResponse.toString());
    }
}
