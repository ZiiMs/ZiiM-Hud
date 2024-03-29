package me.ziim.ziimhud.utils;

import java.awt.*;

public class ColorHelper {

    public int r, g, b, a;

    public ColorHelper() {
        this(255, 255, 255, 255);
    }

    public ColorHelper(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        a = 255;
        validate();
    }

    public ColorHelper(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

        validate();
    }

    public ColorHelper(int packed) {
        r = toRGBAR(packed);
        g = toRGBAG(packed);
        b = toRGBAB(packed);
        a = toRGBAA(packed);
    }

    public static int getRainbow(float seconds, float saturation, float brightness) {
        float hue = (System.currentTimeMillis() % (int) (seconds * 1000))/ (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public ColorHelper(Color color) {
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = color.getAlpha();
    }

    public ColorHelper(ColorHelper color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    public static int fromRGBA(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + (b << 0) + (a << 24);
    }

    public static int toRGBAR(int color) {
        return (color >> 16) & 0x000000FF;
    }

    public static int toRGBAG(int color) {
        return (color >> 8) & 0x000000FF;
    }

    public static int toRGBAB(int color) {
        return (color >> 0) & 0x000000FF;
    }

    public static int toRGBAA(int color) {
        return (color >> 24) & 0x000000FF;

    }

    public static ColorHelper fromHsv(double h, double s, double v) {
        double hh, p, q, t, ff;
        int i;
        double r, g, b;

        if (s <= 0.0) {       // < is bogus, just shuts up warnings
            r = v;
            g = v;
            b = v;
            return new ColorHelper((int) (r * 255), (int) (g * 255), (int) (b * 255), 255);
        }
        hh = h;
        if (hh >= 360.0) hh = 0.0;
        hh /= 60.0;
        i = (int) hh;
        ff = hh - i;
        p = v * (1.0 - s);
        q = v * (1.0 - (s * ff));
        t = v * (1.0 - (s * (1.0 - ff)));

        switch (i) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;

            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            case 5:
            default:
                r = v;
                g = p;
                b = q;
                break;
        }
        return new ColorHelper((int) (r * 255), (int) (g * 255), (int) (b * 255), 255);
    }

    public void set(ColorHelper value) {
        r = value.r;
        g = value.g;
        b = value.b;
        a = value.a;

        validate();
    }

    public void validate() {
        if (r < 0) r = 0;
        else if (r > 255) r = 255;

        if (g < 0) g = 0;
        else if (g > 255) g = 255;

        if (b < 0) b = 0;
        else if (b > 255) b = 255;

        if (a < 0) a = 0;
        else if (a > 255) a = 255;
    }

    public boolean isZero() {
        return r == 0 && g == 0 && b == 0 && a == 0;
    }

    public int getPacked() {
        return fromRGBA(r, g, b, a);
    }

    @Override
    public String toString() {
        return r + " " + g + " " + b + " " + a;
    }

}
