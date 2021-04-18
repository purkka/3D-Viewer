package graphics;

import javafx.scene.paint.Color;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AllColors {
    public static List<Color> allColors() throws ClassNotFoundException, IllegalAccessException {
        List<Color> colors = new ArrayList<>();
        Class<?> clazz = Class.forName("javafx.scene.paint.Color");
        Field[] field = clazz.getFields();
        for (Field f : field) {
            Object obj = f.get(null);
            if (obj instanceof Color) {
                colors.add((Color) obj);
            }
        }
        return colors;
    }
}