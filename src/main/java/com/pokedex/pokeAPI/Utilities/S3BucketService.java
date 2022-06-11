package com.pokedex.pokeAPI.Utilities;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3BucketService {
    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${s3Bucket.bucketName}")
    private String bucketName;

    public InputStream test(String fileName) {
        S3Object s3object = amazonS3Client.getObject(new GetObjectRequest(bucketName, fileName));
        return s3object.getObjectContent();
    }
}
