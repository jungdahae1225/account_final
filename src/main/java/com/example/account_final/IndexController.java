package com.example.account_final;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public ResponseEntity index() {
        return null;
    }

}
