package com.jxw.server.entity;

import lombok.Data;

@Data
public class BodyAngleData {
    private Double wristAngle;    // 手腕角度
    private Double elbowAngle;    // 肘部角度
    private Double armAngle;      // 手臂角度
    private Double bodyAngle;     // 身体角度
    private Double kneeAngle;     // 膝盖角度

    // 无参构造方法
    public BodyAngleData() {
    }

    // 有参构造方法
    public BodyAngleData(Double wristAngle, Double elbowAngle, Double armAngle, Double bodyAngle, Double kneeAngle) {
        this.wristAngle = wristAngle;
        this.elbowAngle = elbowAngle;
        this.armAngle = armAngle;
        this.bodyAngle = bodyAngle;
        this.kneeAngle = kneeAngle;
    }

    // Getter 方法
    public Double getWristAngle() {
        return wristAngle;
    }

    public Double getElbowAngle() {
        return elbowAngle;
    }

    public Double getArmAngle() {
        return armAngle;
    }

    public Double getBodyAngle() {
        return bodyAngle;
    }

    public Double getKneeAngle() {
        return kneeAngle;
    }

    // Setter 方法
    public void setWristAngle(Double wristAngle) {
        this.wristAngle = wristAngle;
    }

    public void setElbowAngle(Double elbowAngle) {
        this.elbowAngle = elbowAngle;
    }

    public void setArmAngle(Double armAngle) {
        this.armAngle = armAngle;
    }

    public void setBodyAngle(Double bodyAngle) {
        this.bodyAngle = bodyAngle;
    }

    public void setKneeAngle(Double kneeAngle) {
        this.kneeAngle = kneeAngle;
    }

    // toString 方法
    @Override
    public String toString() {
        return "BodyAngleData{" +
                "wristAngle=" + wristAngle +
                ", elbowAngle=" + elbowAngle +
                ", armAngle=" + armAngle +
                ", bodyAngle=" + bodyAngle +
                ", kneeAngle=" + kneeAngle +
                '}';
    }
}
