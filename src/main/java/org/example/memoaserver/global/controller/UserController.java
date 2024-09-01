package org.example.memoaserver.global.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @PostMapping("/save-follow")
    public ResponseEntity<?> saveFollow(@RequestBody) {
        
    }

}

