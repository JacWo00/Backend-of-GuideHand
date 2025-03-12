package com.jxw.server;

import com.jxw.server.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenTest {
    @Test
    public void generateTokenTest(){
        System.out.println(JwtUtil.genAccessToken("1"));
    }
}
