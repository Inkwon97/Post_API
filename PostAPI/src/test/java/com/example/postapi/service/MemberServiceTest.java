package com.example.postapi.service;

import com.example.postapi.controller.request.MemberRequestDto;
import com.example.postapi.controller.response.ResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    @Test
    public void test() {
        int a = 3;
        Assertions.assertThat(a).isEqualTo(3);

    }

}