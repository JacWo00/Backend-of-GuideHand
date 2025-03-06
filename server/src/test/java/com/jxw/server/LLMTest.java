package com.jxw.server;

import com.jxw.server.entity.LLMInput;
import com.jxw.server.entity.UserInfo;
import com.jxw.server.service.IUserInfoService;
import com.jxw.server.service.impl.LLMService;
import com.jxw.server.util.llm.HttpSSE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LLMTest {
    @Autowired
    LLMService llmService;

    @Autowired
    IUserInfoService userInfoService;

    @Autowired
    HttpSSE httpSSE;

    @Test
    public void test1() {
        //userInfoService.save(new UserInfo("1","jxw","1",1.88,188.0,"1","1",21,null));
        LLMInput example = new LLMInput(70.0, 75.0, 0.0, 170.0, 170.0, 80.0, 5.0, 170.0, 120.0, 40.0,"三分");
        System.out.println(llmService.analysis(example, "1"));
    }
}
