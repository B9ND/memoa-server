package org.example.memoaserver.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.image.dto.response.ImageResponse;
import org.example.memoaserver.domain.image.exception.ImageNotFoundException;
import org.example.memoaserver.domain.image.exception.ImageTypeException;
import org.example.memoaserver.domain.image.exception.ImageUploadException;
import org.example.memoaserver.domain.image.exception.enums.ImageExceptionStatusCode;
import org.example.memoaserver.domain.image.support.ImageValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final ImageValidator imageValidator;

    public ImageResponse upload(MultipartFile image) throws IOException {
        imageValidator.validateImageFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new ImageNotFoundException();
        }
        return ImageResponse.builder()
                .url(uploadImageToS3(image))
                .build();
    }

    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + "_" + originalFilename;
        byte[] bytes = image.getBytes();

        ObjectMetadata metadata = createMetadata(originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase(), bytes);

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            amazonS3.putObject(createPutObjectRequest(s3FileName, byteArrayInputStream, metadata));
        } catch (Exception e) {
            throw new ImageUploadException();
        }

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    private PutObjectRequest createPutObjectRequest(String name, ByteArrayInputStream byteStream, ObjectMetadata metadata) {
        return new PutObjectRequest(bucket, name, byteStream, metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead);
    }

    private ObjectMetadata createMetadata(String extension, byte[] bytes) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageValidator.getContentType(extension));
        metadata.setContentLength(bytes.length);
        return metadata;
    }
}