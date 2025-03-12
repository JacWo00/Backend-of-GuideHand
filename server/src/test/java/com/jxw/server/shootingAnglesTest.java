package com.jxw.server;

import com.jxw.server.entity.ShootingAngles;
import com.jxw.server.entity.UserTrainingRecords;
import com.jxw.server.service.IShootingAnglesService;
import com.jxw.server.service.IUserTrainingRecordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
public class shootingAnglesTest {
    @Autowired
    IShootingAnglesService shootingAnglesService;

    @Autowired
    IUserTrainingRecordsService userTrainingRecordsService;

    @Test
    public void test1() {
        ShootingAngles angle1 = new ShootingAngles();
        angle1.setUserId("user_001");
        angle1.setRecordId(UUID.randomUUID().toString()); // 随机生成唯一记录ID
        angle1.setAimingElbowAngle(90.5);   // 准备时肘部角度（90°附近）
        angle1.setAimingArmAngle(45.3);     // 准备时大臂与躯干夹角
        angle1.setAimingBodyAngle(15.2);    // 身体前倾角度
        angle1.setAimingKneeAngle(30.1);    // 膝盖弯曲角度
        angle1.setReleaseElbowAngle(85.0);  // 出手时肘部略微伸展
        angle1.setReleaseArmAngle(40.0);    // 手臂跟进角度
        angle1.setReleaseBodyAngle(10.0);   // 身体保持稳定
        angle1.setReleaseKneeAngle(25.0);   // 膝盖自然伸直
        angle1.setReleaseWristAngle(20.0);  // 手腕跟随拨球
        angle1.setReleaseBallAngle(45.0);   // 球的抛物线角度
        angle1.setTheme("mid_shoot");       // 中距离投篮

        ShootingAngles angle2 = new ShootingAngles();
        angle2.setUserId("user_002");
        angle2.setRecordId("record_003");   // 固定测试ID
        angle2.setAimingElbowAngle(95.0);   // 肘部角度略高（三分需要更多力量）
        angle2.setAimingArmAngle(50.0);     // 大臂展开角度更大
        angle2.setAimingBodyAngle(20.0);    // 身体前倾幅度增加
        angle2.setAimingKneeAngle(35.0);   // 膝盖更深弯曲
        angle2.setReleaseElbowAngle(90.0);  // 肘部完全伸展
        angle2.setReleaseArmAngle(45.0);    // 手臂跟进更充分
        angle2.setReleaseBodyAngle(15.0);   // 身体伴随起跳
        angle2.setReleaseKneeAngle(30.0);   // 腿部发力更明显
        angle2.setReleaseWristAngle(25.0);  // 手腕拨球幅度更大
        angle2.setReleaseBallAngle(50.0);   // 更高抛物线
        angle2.setTheme("three_point_shoot");

        ShootingAngles angle3 = new ShootingAngles();
        angle3.setUserId("user_003");
        angle3.setRecordId("record_error_001");
        angle3.setAimingElbowAngle(75.0);   // 肘部过低（典型错误）
        angle3.setAimingArmAngle(30.0);     // 手臂未充分展开
        angle3.setAimingBodyAngle(5.0);     // 身体直立（缺乏发力）
        angle3.setAimingKneeAngle(20.0);   // 膝盖弯曲不足
        angle3.setReleaseElbowAngle(70.0); // 肘部未完全伸展
        angle3.setReleaseArmAngle(35.0);   // 手臂跟进不足
        angle3.setReleaseBodyAngle(2.0);    // 身体后仰
        angle3.setReleaseKneeAngle(15.0);  // 腿部发力中断
        angle3.setReleaseWristAngle(10.0);  // 手腕僵硬
        angle3.setReleaseBallAngle(35.0);   // 抛物线太平
        angle3.setTheme("mid_shoot");       // 用于错误分析
        shootingAnglesService.save(angle1);
        shootingAnglesService.save(angle2);
        shootingAnglesService.save(angle3);
    }

    @Test
    public void test2(){
        System.out.println(userTrainingRecordsService.findByUserIdAndRecordId("user_001","record_002"));
    }

    @Test
    public void test3(){
        UserTrainingRecords record1 = new UserTrainingRecords();
        record1.setRecordId("record_002");
        record1.setUserId("user_001");
        record1.setTrainingDate(LocalDate.of(2023, 10, 1));
        record1.setShootingType("three_point_shoot");
        record1.setTrainingMethod("multiple");
        record1.setAttempts(100);
        record1.setHits(45);
        record1.setAiAnalysis("肘部角度在释放阶段偏小（平均85°），建议增加伸展幅度");
        record1.setAiSuggestions("1. 调整投篮姿势；2. 加强核心力量训练");
        record1.setWeaknessPoints("出手速度不稳定，右侧偏移明显");
        userTrainingRecordsService.save(record1);
    }
}
