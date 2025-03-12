package com.jxw.server.service;

import com.jxw.server.entity.ShootingAngles;

public interface IShootingAnglesService {
    Boolean save(ShootingAngles shootingAngles);

    ShootingAngles getShootingAngles(String userId, String recordId);
}
