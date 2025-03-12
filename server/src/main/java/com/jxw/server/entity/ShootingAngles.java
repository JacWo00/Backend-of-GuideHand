package com.jxw.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;

public class ShootingAngles {
    @TableField("user_id")
    private String userId;              // 用户的ID
    @TableField("record_id")
    private String recordId;           // 训练记录的ID
    @TableField("aiming_elbow_angle")
    private Double aimingElbowAngle;   // 准备时的肘部角度
    @TableField("aiming_arm_angle")
    private Double aimingArmAngle;     // 准备时的手臂角度
    @TableField("aiming_body_angle")
    private Double aimingBodyAngle;    // 准备时的身体角度
    @TableField("aiming_knee_angle")
    private Double aimingKneeAngle;    // 准备时的膝盖角度
    @TableField("release_elbow_angle")
    private Double releaseElbowAngle;  // 释放时的肘部角度
    @TableField("release_arm_angle")
    private Double releaseArmAngle;    // 释放时的手臂角度
    @TableField("release_body_angle")
    private Double releaseBodyAngle;   // 释放时的身体角度
    @TableField("release_knee_angle")
    private Double releaseKneeAngle;   // 释放时的膝盖角度
    @TableField("release_wrist_angle")
    private Double releaseWristAngle;  // 释放时的手腕角度
    @TableField("release_ball_angle")
    private Double releaseBallAngle;   // 释放时的球的角度
    @TableField("theme")
    private String theme;              // 投篮训练的主题：mid shoot / three point shoot

    public ShootingAngles(String userId, String recordId, Double aimingElbowAngle, Double aimingArmAngle, Double aimingBodyAngle, Double aimingKneeAngle, Double releaseElbowAngle, Double releaseArmAngle, Double releaseBodyAngle, Double releaseKneeAngle, Double releaseWristAngle, Double releaseBallAngle, String theme) {
        this.userId = userId;
        this.recordId = recordId;
        this.aimingElbowAngle = aimingElbowAngle;
        this.aimingArmAngle = aimingArmAngle;
        this.aimingBodyAngle = aimingBodyAngle;
        this.aimingKneeAngle = aimingKneeAngle;
        this.releaseElbowAngle = releaseElbowAngle;
        this.releaseArmAngle = releaseArmAngle;
        this.releaseBodyAngle = releaseBodyAngle;
        this.releaseKneeAngle = releaseKneeAngle;
        this.releaseWristAngle = releaseWristAngle;
        this.releaseBallAngle = releaseBallAngle;
        this.theme = theme;
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("以下是用户罚球投篮的姿态数据：\n");
        sb.append("本次训练的主题为：").append(theme).append("\n");
        sb.append("蓄力瞄准阶段：小臂与大臂夹角").append(aimingElbowAngle).append("度，")
                .append("躯干与大臂夹角").append(aimingArmAngle).append("度，")
                .append("躯干前倾").append(aimingBodyAngle).append("度，")
                .append("膝盖弯曲角度").append(aimingKneeAngle).append("度。\n");
        sb.append("出手阶段：小臂与大臂夹角").append(releaseElbowAngle).append("度，")
                .append("大臂与躯干夹角").append(releaseArmAngle).append("度，")
                .append("膝盖弯曲角度").append(releaseKneeAngle).append("度，")
                .append("手腕与小臂夹角").append(releaseWristAngle).append("度，")
                .append("躯干前倾").append(releaseBodyAngle).append("度，")
                .append("出球角度为").append(releaseBallAngle).append("度。");

        return sb.toString();
    }

    // 无参构造方法
    public ShootingAngles() {
    }

    // 有参构造方法
    public ShootingAngles(Double aimingElbowAngle, Double aimingArmAngle, Double aimingBodyAngle,
                          Double aimingKneeAngle, Double releaseElbowAngle, Double releaseArmAngle,
                          Double releaseBodyAngle, Double releaseKneeAngle, Double releaseWristAngle,
                          Double releaseBallAngle, String theme) {
        this.aimingElbowAngle = aimingElbowAngle;
        this.aimingArmAngle = aimingArmAngle;
        this.aimingBodyAngle = aimingBodyAngle;
        this.aimingKneeAngle = aimingKneeAngle;
        this.releaseElbowAngle = releaseElbowAngle;
        this.releaseArmAngle = releaseArmAngle;
        this.releaseBodyAngle = releaseBodyAngle;
        this.releaseKneeAngle = releaseKneeAngle;
        this.releaseWristAngle = releaseWristAngle;
        this.releaseBallAngle = releaseBallAngle;
        this.theme = theme;
    }



    // Getter 方法
    public Double getAimingElbowAngle() {
        return aimingElbowAngle;
    }

    public Double getAimingArmAngle() {
        return aimingArmAngle;
    }

    public Double getAimingBodyAngle() {
        return aimingBodyAngle;
    }

    public Double getAimingKneeAngle() {
        return aimingKneeAngle;
    }

    public Double getReleaseElbowAngle() {
        return releaseElbowAngle;
    }

    public Double getReleaseArmAngle() {
        return releaseArmAngle;
    }

    public Double getReleaseBodyAngle() {
        return releaseBodyAngle;
    }

    public Double getReleaseKneeAngle() {
        return releaseKneeAngle;
    }

    public Double getReleaseWristAngle() {
        return releaseWristAngle;
    }

    public Double getReleaseBallAngle() {
        return releaseBallAngle;
    }

    // Setter 方法
    public void setAimingElbowAngle(Double aimingElbowAngle) {
        this.aimingElbowAngle = aimingElbowAngle;
    }

    public void setAimingArmAngle(Double aimingArmAngle) {
        this.aimingArmAngle = aimingArmAngle;
    }

    public void setAimingBodyAngle(Double aimingBodyAngle) {
        this.aimingBodyAngle = aimingBodyAngle;
    }

    public void setAimingKneeAngle(Double aimingKneeAngle) {
        this.aimingKneeAngle = aimingKneeAngle;
    }

    public void setReleaseElbowAngle(Double releaseElbowAngle) {
        this.releaseElbowAngle = releaseElbowAngle;
    }

    public void setReleaseArmAngle(Double releaseArmAngle) {
        this.releaseArmAngle = releaseArmAngle;
    }

    public void setReleaseBodyAngle(Double releaseBodyAngle) {
        this.releaseBodyAngle = releaseBodyAngle;
    }

    public void setReleaseKneeAngle(Double releaseKneeAngle) {
        this.releaseKneeAngle = releaseKneeAngle;
    }

    public void setReleaseWristAngle(Double releaseWristAngle) {
        this.releaseWristAngle = releaseWristAngle;
    }

    public void setReleaseBallAngle(Double releaseBallAngle) {
        this.releaseBallAngle = releaseBallAngle;
    }



    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getUserId() {
        return userId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        return "ShootingAngles{" +
                "userId='" + userId + '\'' +
                ", recordId='" + recordId + '\'' +
                ", aimingElbowAngle=" + aimingElbowAngle +
                ", aimingArmAngle=" + aimingArmAngle +
                ", aimingBodyAngle=" + aimingBodyAngle +
                ", aimingKneeAngle=" + aimingKneeAngle +
                ", releaseElbowAngle=" + releaseElbowAngle +
                ", releaseArmAngle=" + releaseArmAngle +
                ", releaseBodyAngle=" + releaseBodyAngle +
                ", releaseKneeAngle=" + releaseKneeAngle +
                ", releaseWristAngle=" + releaseWristAngle +
                ", releaseBallAngle=" + releaseBallAngle +
                ", theme='" + theme + '\'' +
                '}';
    }
}
