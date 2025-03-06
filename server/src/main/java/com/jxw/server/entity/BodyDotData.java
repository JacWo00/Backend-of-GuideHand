package com.jxw.server.entity;
import lombok.Data;

import java.awt.*;
import java.util.AbstractMap.SimpleEntry;


public class BodyDotData {
    private Point headPosition;
    private Point wristPosition;
    private Point figureRootPosition;
    private Point elbowPosition;
    private Point shoulderPosition;
    private Point bodyUpperPosition;
    private Point bodyLowerPosition;
    private Point hipPosition;
    private Point kneePosition;
    private Point anklePosition;

    // 无参构造
    public BodyDotData() {
    }

    // 有参构造
    public BodyDotData(Point headPosition, Point wristPosition, Point figureRootPosition, Point elbowPosition,
                       Point shoulderPosition, Point bodyUpperPosition, Point bodyLowerPosition, Point hipPosition,
                       Point kneePosition, Point anklePosition) {
        this.headPosition = headPosition;
        this.wristPosition = wristPosition;
        this.figureRootPosition = figureRootPosition;
        this.elbowPosition = elbowPosition;
        this.shoulderPosition = shoulderPosition;
        this.bodyUpperPosition = bodyUpperPosition;
        this.bodyLowerPosition = bodyLowerPosition;
        this.hipPosition = hipPosition;
        this.kneePosition = kneePosition;
        this.anklePosition = anklePosition;
    }

    // Getters
    public Point getHeadPosition() {
        return headPosition;
    }

    public Point getWristPosition() {
        return wristPosition;
    }

    public Point getFigureRootPosition() {
        return figureRootPosition;
    }

    public Point getElbowPosition() {
        return elbowPosition;
    }

    public Point getShoulderPosition() {
        return shoulderPosition;
    }

    public Point getBodyUpperPosition() {
        return bodyUpperPosition;
    }

    public Point getBodyLowerPosition() {
        return bodyLowerPosition;
    }

    public Point getHipPosition() {
        return hipPosition;
    }

    public Point getKneePosition() {
        return kneePosition;
    }

    public Point getAnklePosition() {
        return anklePosition;
    }

    // Setters
    public void setHeadPosition(Point headPosition) {
        this.headPosition = headPosition;
    }

    public void setWristPosition(Point wristPosition) {
        this.wristPosition = wristPosition;
    }

    public void setFigureRootPosition(Point figureRootPosition) {
        this.figureRootPosition = figureRootPosition;
    }

    public void setElbowPosition(Point elbowPosition) {
        this.elbowPosition = elbowPosition;
    }

    public void setShoulderPosition(Point shoulderPosition) {
        this.shoulderPosition = shoulderPosition;
    }

    public void setBodyUpperPosition(Point bodyUpperPosition) {
        this.bodyUpperPosition = bodyUpperPosition;
    }

    public void setBodyLowerPosition(Point bodyLowerPosition) {
        this.bodyLowerPosition = bodyLowerPosition;
    }

    public void setHipPosition(Point hipPosition) {
        this.hipPosition = hipPosition;
    }

    public void setKneePosition(Point kneePosition) {
        this.kneePosition = kneePosition;
    }

    public void setAnklePosition(Point anklePosition) {
        this.anklePosition = anklePosition;
    }

    // toString 方法
    @Override
    public String toString() {
        return "BodyDotData{" +
                "headPosition=" + headPosition +
                ", wristPosition=" + wristPosition +
                ", figureRootPosition=" + figureRootPosition +
                ", elbowPosition=" + elbowPosition +
                ", shoulderPosition=" + shoulderPosition +
                ", bodyUpperPosition=" + bodyUpperPosition +
                ", bodyLowerPosition=" + bodyLowerPosition +
                ", hipPosition=" + hipPosition +
                ", kneePosition=" + kneePosition +
                ", anklePosition=" + anklePosition +
                '}';
    }
}
