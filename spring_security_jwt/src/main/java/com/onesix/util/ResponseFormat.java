package com.onesix.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseFormat {

    public static ResponseEntity<Object> generateResponse(String message, int hs, Object res) {

        HashMap<String, Object> response = new HashMap<>();
        response.put("data", res);
        response.put("message", message);
        response.put("status", hs);

        return new ResponseEntity<Object>(response, HttpStatus.valueOf(hs));

    }

}
