package com.beytcho.Beytcho.Services;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class AWS_S3_Service {

    private final String bucketName = "bey-ell";

    @Value("${aws.s3.access}")
    private String awsS3acKey;

    @Value("${aws.s3.secret key")
    private String awsS3secKey;

    public String saveImageToS3(MultipartFile photo){

        try{


            String s3FileName = photo.getOriginalFilename();

            //create aws creds using keys
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3acKey, awsS3secKey);

            //make s3 cliend with config creds and region
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();

            //get input stream from photo
            InputStream inputStream = photo.getInputStream();

            // set metadata for objects
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            //create a put request to upload the image to s3
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, metadata);
            s3Client.putObject(putObjectRequest);

            return "https//" + bucketName + ".s3.us-east-1.amazonaws.com/"+s3FileName;
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Unable to upload image to s3 bucket" + e.getMessage());
        }
    }


}
