package Modals;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import com.adobe.creativesdk.aviary.internal.headless.moa.Moa;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MaterialColorPalette {
    public static final int AMBER_500 = -16121;
    public static final int BLUE_500 = -14575885;
    public static final int BLUE_GREY_500 = -10453621;
    public static final int BROWN_500 = -8825528;
    public static final int CYAN_500 = -16728876;
    public static final int DEEP_ORANGE_500 = -43230;
    public static final int DEEP_PURPLE_500 = -10011977;
    public static final int GREEN_500 = -11751600;
    public static final int GREY_500 = -6381922;
    public static final int INDIGO_500 = -12627531;
    public static final int LIGHT_BLUE_500 = -16537100;
    public static final int LIGHT_GREEN_500 = -7617718;
    public static final int LIME_500 = -3285959;
    private static final List<MaterialColorPalette> MATERIAL_PALETTES = new ArrayList();
    public static final int ORANGE_500 = -26624;
    public static final int PINK_500 = -1499549;
    public static final int PURPLE_500 = -6543440;
    private static final Random RANDOM = new Random();
    public static final int RED_500 = -769226;
    public static final int TEAL_500 = -16738680;
    public static final int YELLOW_500 = -5317;
    private final HashMap<String, Integer> palette = new HashMap();

    static {
        MATERIAL_PALETTES.add(new MaterialColorPalette(RED_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(PINK_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(PURPLE_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(DEEP_PURPLE_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(INDIGO_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(BLUE_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(LIGHT_BLUE_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(CYAN_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(TEAL_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(GREEN_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(LIGHT_GREEN_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(LIME_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(YELLOW_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(AMBER_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(ORANGE_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(DEEP_ORANGE_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(BROWN_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(GREY_500));
        MATERIAL_PALETTES.add(new MaterialColorPalette(BLUE_GREY_500));
    }

    public static int getRandomColor(String key) {
        return ((MaterialColorPalette) MATERIAL_PALETTES.get(RANDOM.nextInt(MATERIAL_PALETTES.size()))).getColor(key);
    }

    public static int shadeColor(int color, double percent) {
        return shadeColor(String.format("#%06X", new Object[]{Integer.valueOf(ViewCompat.MEASURED_SIZE_MASK & color)}), percent);
    }

    public static int shadeColor(String color, double percent) {
        long f = Long.parseLong(color.substring(1), 16);
        double t = percent < Moa.kMemeFontVMargin ? Moa.kMemeFontVMargin : 255.0d;
        double p = percent < Moa.kMemeFontVMargin ? -1.0d * percent : percent;
        long R = f >> 16;
        long G = (f >> 8) & 255;
        long B = f & 255;
        return Color.rgb((int) (Math.round((t - ((double) R)) * p) + R), (int) (Math.round((t - ((double) G)) * p) + G), (int) (Math.round((t - ((double) B)) * p) + B));
    }

    public MaterialColorPalette(int primary) {
        this.palette.put("50", Integer.valueOf(shadeColor(primary, 0.9d)));
        this.palette.put("100", Integer.valueOf(shadeColor(primary, 0.7d)));
        this.palette.put("200", Integer.valueOf(shadeColor(primary, 0.5d)));
        this.palette.put("300", Integer.valueOf(shadeColor(primary, 0.333d)));
        this.palette.put("400", Integer.valueOf(shadeColor(primary, 0.166d)));
        this.palette.put("500", Integer.valueOf(primary));
        this.palette.put("600", Integer.valueOf(shadeColor(primary, -0.125d)));
        this.palette.put("700", Integer.valueOf(shadeColor(primary, -0.25d)));
        this.palette.put("800", Integer.valueOf(shadeColor(primary, -0.375d)));
        this.palette.put("900", Integer.valueOf(shadeColor(primary, -0.5d)));
        this.palette.put("A100", Integer.valueOf(shadeColor(primary, 0.7d)));
        this.palette.put("A200", Integer.valueOf(shadeColor(primary, 0.5d)));
        this.palette.put("A400", Integer.valueOf(shadeColor(primary, 0.166d)));
        this.palette.put("A700", Integer.valueOf(shadeColor(primary, -0.25d)));
    }

    public int getColor(String key) {
        return ((Integer) this.palette.get(key)).intValue();
    }

    public void putColor(String key, int color) {
        this.palette.put(key, Integer.valueOf(color));
    }
}
