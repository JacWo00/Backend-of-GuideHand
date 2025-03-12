//package com.jxw.server.util;
//
//import java.util.UUID;
//
//class ShootingDataGenerator {
//    public static void main(String[] args) {
//        for (int i = 0; i < 3; i++) {
//            System.out.println(generateSampleData());
//        }
//    }
//
//    private static String generateSampleData() {
//        return String.format(
//                "{\n" +
//                        "  \"userId\": \"%s\",\n" +
//                        "  \"recordId\": \"%s\",\n" +
//                        "  \"aimingElbowAngle\": %.1f,\n" +
//                        "  \"aimingArmAngle\": %.1f,\n" +
//                        "  \"aimingBodyAngle\": %.1f,\n" +
//                        "  \"aimingKneeAngle\": %.1f,\n" +
//                        "  \"releaseElbowAngle\": %.1f,\n" +
//                        "  \"releaseArmAngle\": %.1f,\n" +
//                        "  \"releaseBodyAngle\": %.1f,\n" +
//                        "  \"releaseKneeAngle\": %.1f,\n" +
//                        "  \"releaseWristAngle\": %.1f,\n" +
//                        "  \"releaseBallAngle\": %.1f,\n" +
//                        "  \"theme\": \"%s\"\n" +
//                        "}",
//                UUID.randomUUID(),
//                UUID.randomUUID(),
//                getRandomAngle(),
//                getRandomAngle(),
//                getRandomAngle(),
//                getRandomAngle(),
//                getRandomAngle(),
//                getRandomAngle(),
//                getRandomAngle(),
//                getRandomAngle(),
//                getRandomAngle(),
//                getRandomAngle(),
//                i % 2 == 0 ? "mid_shoot" : "three_point_shoot"
//        );
//    }
//
//    private static double getRandomAngle() {
//        return Math.round(Math.random() * 100 * 10) / 10.0;
//    }
//}
