package org.example.memoaserver.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.image.dto.res.ImageResponse;
import org.example.memoaserver.domain.image.exception.ImageNotFoundException;
import org.example.memoaserver.domain.image.exception.ImageTypeException;
import org.example.memoaserver.domain.image.exception.ImageUploadException;
import org.example.memoaserver.domain.image.exception.enums.ImageExceptionStatusCode;
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
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private void validateImageFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new ImageTypeException(ImageExceptionStatusCode.NO_FILE_TYPE);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensionList = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");

        if (!allowedExtensionList.contains(extension)) {
            throw new ImageTypeException(ImageExceptionStatusCode.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private String getContentType(String extension) {
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }

    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + "_" + originalFilename;

        byte[] bytes = image.getBytes();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(getContentType(extension));
        metadata.setContentLength(bytes.length);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucket, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new ImageUploadException();
        }

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    public ImageResponse upload(MultipartFile image) throws IOException {
        validateImageFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new ImageNotFoundException();
        }

        return ImageResponse.builder()
                .url(uploadImageToS3(image))
                .build();
    }
}