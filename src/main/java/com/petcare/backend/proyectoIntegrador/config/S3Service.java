package com.petcare.backend.proyectoIntegrador.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3;

    @Value("${BUCKET_NAME}")
    private String BUCKET_NAME;

//    @Value("${WS_SECRET_ACCESS_KEY}")
//    private String WS_SECRET_ACCESS_KEY;
//
//    @Value("${AWS_ACCESS_KEY_ID}")
//    private String AWS_ACCESS_KEY_ID;



    public S3Service(@Value("${AWS_ACCESS_KEY_ID}") String accessKeyId,
                     @Value("${AWS_SECRET_ACCESS_KEY}") String secretAccessKey) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public String uploadFile(byte[] fileBytes, String originalFileName) {
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

        // Subir el archivo a S3
        s3.putObject(PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(uniqueFileName)
                        .build(),
                RequestBody.fromBytes(fileBytes));

        return "https://" + BUCKET_NAME + ".s3.amazonaws.com/" + uniqueFileName;
    }
}
