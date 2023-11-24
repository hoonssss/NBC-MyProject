package com.example.backendgram;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandle {

    @GetMapping("/error")
    public String handleError() {

        return "error";
    }
}