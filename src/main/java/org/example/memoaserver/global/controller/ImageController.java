package org.example.memoaserver.global.controller;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.image.service.UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestPart(value = "image", required = true) MultipartFile image) throws IOException {
        return ResponseEntity.ok().body(uploadService.upload(image));
    }
}
