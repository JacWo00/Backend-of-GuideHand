package com.jxw.server.service;

import java.awt.*;
import java.io.IOException;
import java.util.AbstractMap;

public interface IHoopDetectionService {
    public Point[] detectHoopPosition(String imagePath) throws IOException, InterruptedException;
}
