package com.jxw.server.service;

import com.jxw.server.entity.BodyAngleData;
import com.jxw.server.entity.BodyDotData;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
public interface IPoseDetectionService {
    public BodyDotData dotDataDetect(String imagePath) throws RuntimeException, IOException, InterruptedException;

    public BodyAngleData angleDataDetect(String imagePath) throws IOException, InterruptedException;
}
