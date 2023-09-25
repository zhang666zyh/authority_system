package com.zhang.code;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
class CodeApplicationTests {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void test1(){
        System.out.println(passwordEncoder.encode("123456"));
    }
}
