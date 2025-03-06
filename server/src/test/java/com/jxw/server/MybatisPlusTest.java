package com.jxw.server;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.jxw.server.entity.User;
import com.jxw.server.entity.UserInfo;
import com.jxw.server.mapper.UserMapper;
import com.jxw.server.service.IUserInfoService;
import com.jxw.server.service.impl.UserInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisPlusTest {
    @Autowired
    private UserMapper userMapper;

//    @Test
//    public void testSelect() {
//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//
//        Assert.isTrue(5 == userList.size(), "");
//        userList.forEach(System.out::println);
//    }
//
//    @Test
//    public void testIService(@Autowired IUserInfoService userInfoService){
//        UserInfo userInfo = new UserInfo();
//
//        userInfo.setUserId(1);
//        userInfo.setUsername("Jxw");
//
//        userInfoService.save(userInfo);
//
//        UserInfo byId = userInfoService.getById(1);
//
//        // 打印到控制台
//        if (byId != null) {
//            System.out.println("User ID: " + byId.getUserId());
//            System.out.println("Username: " + byId.getUsername());
//        } else {
//            System.out.println("User not found");
//        }
//
//
//    }

}
