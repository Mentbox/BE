package com.example.mentbox.recording.utility;
import com.example.mentbox.common.exception.AudioFileUploadFailException;
import com.example.mentbox.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, String dirName) {
        String originalFilename = file.getOriginalFilename();
        String ext = this.getExtension(originalFilename);
        String key = dirName + "/" + UUID.randomUUID() + ext;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return key;
        } catch (IOException | SdkClientException e) {
            throw new AudioFileUploadFailException(ErrorCode.AudioFileUploadFail, e);
        }
    }

    public String getS3Url(String key) {
        return "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + key;
    }

    private String getExtension(String filename) {
        int lastDot = filename.lastIndexOf(".");

        return (lastDot != 1) ? filename.substring(lastDot) : "";
    }

    public String generatePresignedUrl(String key) {

        GetObjectRequest getReq = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofDays(7))
                .getObjectRequest(getReq)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }

    public void delete(String key) {
        try {
            s3Client.deleteObject(builder -> builder
                    .bucket(bucket)
                    .key(key)
            );
        } catch (SdkClientException e) {
            System.err.println("S3 파일 삭제 실패: " + key);
        }
    }


}
