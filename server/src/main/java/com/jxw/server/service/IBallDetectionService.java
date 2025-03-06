package com.jxw.server.service;

import java.awt.*;


public interface IBallDetectionService {

    public Point detectBallPosition(String imagePath);

    public Boolean isGoal(Point ballPosition, Point hoopPosition);
}
