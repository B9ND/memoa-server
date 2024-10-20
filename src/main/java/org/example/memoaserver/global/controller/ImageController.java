package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.image.dto.res.ImageResponse;
import org.example.memoaserver.domain.image.service.UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Tag(name = "image", description = "이미지 관련 API")
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final UploadService uploadService;

    @Operation(
            summary = "이미지를 업로드 합니다.",
            description = "이미지를 form 방식으로 전송하면 이미지를 받을 수 있음 (application/json X) \n 이후 이미지 url 을 반환해준다."
    )
    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadImage(
            @RequestPart(value = "image") MultipartFile image) throws IOException {
        return ResponseEntity.ok().body(uploadService.upload(image));
    }
}
