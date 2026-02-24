package com.beytcho.Beytcho.Services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class AWS_S3_Service {

    private final String bucketName = "bey-ell";

    @Value("${aws.s3.access}")
    private String awsS3acKey;

    @Value("${aws.s3.secret}")
    private String awsS3secKey;

    public void deleteImageFromS3(String imageUrl) {
        try {
            BasicAWSCredentials awsCredentials =
                    new BasicAWSCredentials(awsS3acKey, awsS3secKey);

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();

            // Extract file name from full URL
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

            s3Client.deleteObject(bucketName, fileName);

            log.info("Deleted image from S3: {}", fileName);

        } catch (Exception e) {
            log.error("Error deleting file from S3", e);
            throw new RuntimeException("Error deleting file from S3: " + e.getMessage());
        }
    }

    public String saveImageToS3(MultipartFile photo) {
        // Convert MultipartFile to byte array first to allow multiple reads
        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray(photo.getInputStream());
        } catch (IOException e) {
            log.error("Failed to read file bytes: ", e);
            throw new RuntimeException("Unable to read file: " + e.getMessage(), e);
        }

        try {
            String s3FileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.]", "_");
            log.info("Attempting to upload file: {} to S3 bucket: {}", s3FileName, bucketName);
            log.info("File size: {} bytes", bytes.length);
            log.info("File content type: {}", photo.getContentType());

            // Create AWS creds using keys
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3acKey, awsS3secKey);

            // Make s3 client with config creds and region
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();

            // Create a new ByteArrayInputStream from the bytes
            // This can be reset and used multiple times
            InputStream inputStream = new ByteArrayInputStream(bytes);

            // Set metadata for objects
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(photo.getContentType());
            metadata.setContentLength(bytes.length);

            log.info("Starting S3 upload...");

            // Create a put request to upload the image to s3
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, metadata);

            // Configure the request to allow larger buffer for retries
            putObjectRequest.getRequestClientOptions().setReadLimit(Integer.MAX_VALUE);

            PutObjectResult result = s3Client.putObject(putObjectRequest);

            log.info("S3 upload completed. ETag: {}", result.getETag());

            String imageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + s3FileName;
            log.info("Successfully uploaded to: {}", imageUrl);
            return imageUrl;

        } catch (com.amazonaws.services.s3.model.AmazonS3Exception e) {
            log.error("Amazon S3 Exception: {}", e.getErrorCode());
            log.error("Error message: {}", e.getErrorMessage());
            log.error("Status code: {}", e.getStatusCode());
            log.error("Request ID: {}", e.getRequestId());
            e.printStackTrace();
            throw new RuntimeException("S3 Error: " + e.getErrorCode() + " - " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while uploading to S3: ", e);
            e.printStackTrace();
            throw new RuntimeException("Unexpected error uploading to S3: " + e.getMessage(), e);
        }
    }
}