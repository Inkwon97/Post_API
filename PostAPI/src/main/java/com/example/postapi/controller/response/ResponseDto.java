package com.example.postapi.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
  private boolean success;
  private T data;
  private T heart;
  private Error error;

  public static <T> ResponseDto<T> success(T data) {
    return new ResponseDto<>(true, data, null);
  }
  public static <T> ResponseDto<T> success(T data, T heart) {
    return new ResponseDto<>(true, data, heart, null);
  }

  public static <T> ResponseDto<T> fail(String code, String message) {
    return new ResponseDto<>(false, null, new Error(code, message));
  }

  public ResponseDto(boolean success, T data, Error error) {
    this.success = success;
    this.data = data;
    this.error = error;
  }

  @Getter
  @AllArgsConstructor
  static class Error {
    private String code;
    private String message;
  }

}
