package com.example.postapi.controller.response;

import lombok.Getter;

@Getter
public class ImageResponseDto {
    private final String imageUrl;

    public ImageResponseDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
