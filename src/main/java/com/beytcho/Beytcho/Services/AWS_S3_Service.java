package com.beytcho.Beytcho.Services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AWS_S3_Service {

    private final String bucketName = "bey-ell";

    @Value("${aws.s3.access}")
    private String awsS3acKey;

    @Value("${aws.s3.secret key")
    private String awsS3secKey;

    

}
