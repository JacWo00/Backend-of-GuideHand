package com.jxw.server.service.impl;

import com.jxw.server.service.IHoopDetectionService;
import com.jxw.server.util.scriptRunner.ScriptRunner;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.ArrayList;

public class HoopDetectionServiceImpl implements IHoopDetectionService {

    @Override
    public Point[] detectHoopPosition(String imagePath) throws IOException, InterruptedException {
        String scriptPath = "";
        ArrayList<String> output = ScriptRunner.run(scriptPath, imagePath);
        Point[] res = new Point[3];
        for(int i=0;i<3;i++) {
            int x = Integer.parseInt(output.get(i).split(",")[0].trim());
            int y = Integer.parseInt(output.get(i).split(",")[1].trim());
            res[i] = new Point(x,y);
        }
        return res;
    }
}
