package com.example.postapi.controller;

import com.example.postapi.controller.response.ImageResponseDto;
import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/image")
    public ResponseDto<?> upload(MultipartFile image) {
        String imageUrl = imageService.upload(image);
        ImageResponseDto imageResponseDto = new ImageResponseDto(imageUrl);
        return ResponseDto.success(imageResponseDto);
    }
}
