package com.jxw.server.service.impl;

import com.jxw.server.entity.BodyAngleData;
import com.jxw.server.entity.BodyDotData;
import com.jxw.server.service.IPoseDetectionService;
import com.jxw.server.util.scriptRunner.ScriptRunner;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

@Service
public class PoseDetectionServiceImpl implements IPoseDetectionService {

    @Override
    public BodyDotData dotDataDetect(String imagePath) throws IOException, InterruptedException {
//        private AbstractMap.SimpleEntry<Integer, Integer> headPosition;
//        private AbstractMap.SimpleEntry<Integer, Integer> wristPosition;
//        private AbstractMap.SimpleEntry<Integer, Integer> figureRootPosition;
//        private AbstractMap.SimpleEntry<Integer, Integer> elbowPosition;
//        private AbstractMap.SimpleEntry<Integer, Integer> shoulderPosition;
//        private AbstractMap.SimpleEntry<Integer, Integer> bodyUpperPosition;
//        private AbstractMap.SimpleEntry<Integer, Integer> bodyLowerPosition;
//        private AbstractMap.SimpleEntry<Integer, Integer> hipPosition;
//        private AbstractMap.SimpleEntry<Integer, Integer> kneePosition;
//        private AbstractMap.SimpleEntry<Integer, Integer> anklePosition;

            String scriptPath = "";
            ArrayList<String> output = ScriptRunner.run(scriptPath, imagePath);
            BodyDotData bodyDotData = new BodyDotData();
            bodyDotData.setHeadPosition(new Point(Integer.parseInt(output.get(0).split(",")[0]),Integer.parseInt(output.get(0).split(",")[1])));
            bodyDotData.setWristPosition(new Point(Integer.parseInt(output.get(1).split(",")[0]),Integer.parseInt(output.get(1).split(",")[1])));
            bodyDotData.setFigureRootPosition(new Point(Integer.parseInt(output.get(2).split(",")[0]),Integer.parseInt(output.get(2).split(",")[1])));
            bodyDotData.setElbowPosition(new Point(Integer.parseInt(output.get(3).split(",")[0]),Integer.parseInt(output.get(3).split(",")[1])));
            bodyDotData.setShoulderPosition(new Point(Integer.parseInt(output.get(4).split(",")[0]),Integer.parseInt(output.get(4).split(",")[1])));
            bodyDotData.setBodyLowerPosition(new Point(Integer.parseInt(output.get(5).split(",")[0]),Integer.parseInt(output.get(5).split(",")[1])));
            bodyDotData.setBodyUpperPosition(new Point(Integer.parseInt(output.get(6).split(",")[0]),Integer.parseInt(output.get(6).split(",")[1])));
            bodyDotData.setHipPosition(new Point(Integer.parseInt(output.get(7).split(",")[0]),Integer.parseInt(output.get(7).split(",")[1])));
            bodyDotData.setKneePosition(new Point(Integer.parseInt(output.get(8).split(",")[0]),Integer.parseInt(output.get(8).split(",")[1])));
            bodyDotData.setAnklePosition(new Point(Integer.parseInt(output.get(9).split(",")[0]),Integer.parseInt(output.get(9).split(",")[1])));
            return bodyDotData;
    }

    @Override
    public BodyAngleData angleDataDetect(String imagePath) throws IOException, InterruptedException {
//        Double wristAngle;
//        Double elbowAngle;
//        Double armAngle;
//        Double bodyAngle;
//        Double kneeAngle;
        String scriptPath = "";
        ArrayList<String> output = ScriptRunner.run(scriptPath, imagePath);
        BodyAngleData bodyAngleData = new BodyAngleData();
        bodyAngleData.setWristAngle(Double.parseDouble(output.get(0)));
        bodyAngleData.setElbowAngle(Double.parseDouble(output.get(1)));
        bodyAngleData.setArmAngle(Double.parseDouble(output.get(2)));
        bodyAngleData.setBodyAngle(Double.parseDouble(output.get(3)));
        bodyAngleData.setKneeAngle(Double.parseDouble(output.get(4)));
        return bodyAngleData;
    }
}
