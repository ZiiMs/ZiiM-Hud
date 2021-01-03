package me.ziim.ziimhud.utils;

import java.util.Random;

public class Utils {
    private static final Random random = new Random();


    public static double normalize(double val, double max, double min) {
        return (val - min) / (max - min);
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static int random(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static double random(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }


}
