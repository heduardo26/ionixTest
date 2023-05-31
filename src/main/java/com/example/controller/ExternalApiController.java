package com.example.controller;

import com.example.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/external")
public class ExternalApiController {

    private final ExternalApiService externalApiService;


    @PostMapping
    public ResponseEntity<Object>search(@RequestParam String param) {
        return externalApiService.getExternalService(param);
    }

    /*@GetMapping
    public ResponseEntity<Object>getMet(){
        return new ResponseEntity("Si veo esto la seguridad solo aplica para el Post", HttpStatus.OK);
    }*/

}
