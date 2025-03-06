package com.jxw.server.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.awt.Point;

public class AngleCalculator {
    public static double calculateAngle(Point a, Point b, Point c) {
        // 向量 BA = A - B
        int baX = a.x - b.x;
        int baY = a.y - b.y;

        // 向量 BC = C - B
        int bcX = c.x - b.x;
        int bcY = c.y - b.y;

        // 计算点积
        double dotProduct = (baX * bcX) + (baY * bcY);

        // 计算向量的模长
        double magnitudeBA = Math.sqrt(baX * baX + baY * baY);
        double magnitudeBC = Math.sqrt(bcX * bcX + bcY * bcY);

        // 计算余弦值，避免因浮点误差导致 Math.acos 计算出 NaN
        double cosineAngle = dotProduct / (magnitudeBA * magnitudeBC);
        cosineAngle = Math.max(-1.0, Math.min(1.0, cosineAngle));

        // 计算角度（弧度转角度）
        double angle = Math.toDegrees(Math.acos(cosineAngle));

        // 保留两位小数
        return Math.round(angle * 100.0) / 100.0;
    }
}
