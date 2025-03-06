package com.jxw.server.service.impl;

import java.awt.*;

import com.jxw.server.service.IBallDetectionService;
import com.jxw.server.util.scriptRunner.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BallDetectionServiceImpl implements IBallDetectionService {
    @Autowired
    ScriptRunner scriptRunner;

    @Override
    public Point detectBallPosition(String imagePath) {
        try{
            String scriptPath = "";
            ArrayList<String> output = ScriptRunner.run(scriptPath, imagePath);
            int x=Integer.parseInt(output.get(0));
            int y=Integer.parseInt(output.get(1));
            Point res = new Point(x, y);
            return res;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public Boolean isGoal(Point ballPosition, Point hoopPosition) {
        if(ballPosition == null || hoopPosition == null) return false;
        int ballX = ballPosition.x;
        int ballY = ballPosition.y;
        int hoopX = hoopPosition.x;
        int hoopY = hoopPosition.y;
        int distance = (int) Math.sqrt((ballX - hoopX) * (ballX - hoopX) + (ballY - hoopY) * (ballY - hoopY));
        return distance < 50;
    }
}
