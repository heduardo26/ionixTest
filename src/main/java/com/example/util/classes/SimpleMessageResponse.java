package com.example.util.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMessageResponse implements Serializable {
    private static final long serialVersionUID = -8233191197471746350L;
    private String message;
}
