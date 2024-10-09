package cjw.apiserver2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-endpoint")
public class ApiController {

    @GetMapping
    public String getResponse() {
        return "Response from API Server 2";
    }
}