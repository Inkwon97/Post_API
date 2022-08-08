package com.example.postapi.image;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final S3UploaderService s3UploaderService;

    @PostMapping("/api/images")
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        s3UploaderService.upload(multipartFile, "static");
        return "test";
    }

    @DeleteMapping("/api/images")
    public String deleteFile(@RequestBody String filename) {
        s3UploaderService.deleteImage(filename);
        return "delete";
    }

    


}
