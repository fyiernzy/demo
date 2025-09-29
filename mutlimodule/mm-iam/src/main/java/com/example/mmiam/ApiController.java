package com.example.mmiam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiController {

    @GetMapping("/api/read")
    public String readData(HttpServletRequest request) {
        log.info(request.getHeader("Authorization"));
        return "🔒 Protected resource: read access granted!";
    }

    @GetMapping("/api/write")
    public String writeData() {
        return "🔒 Protected resource: write access granted!";
    }
}
