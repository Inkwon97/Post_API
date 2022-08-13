package com.example.postapi.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S3UploaderServiceTest {

    @Autowired
    private S3UploaderService s3UploaderService;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Test
    @DisplayName("이미지 get url 테스트")
    public void getUrl() {
        String imgPath = s3UploaderService.getFileUrl("everytime.png");
        System.out.println(imgPath);
    }
}