package eu.time.aramchecker.lolwiki;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AramChange {
    private static final int ATTRIBUTE_MIN_WIDTH = 14;
    
    private double id;
    private String name;
    
    public Double ability_haste;
    public Double dmg_dealt;
    public Double dmg_taken;
    public Double healing;
    public Double shielding;
    public Double tenacity;
    public Double energy_regen;
    public Double attack_speed;

    public String getPrintString(int parentWidth) {
        Class<?> clazz = this.getClass();

        Field[] declaredFields = clazz.getFields();

        Map<String, String> emptyFields = new HashMap<>();
        List<String> setFields = new ArrayList<>();

        for (Field field : declaredFields) {
            String fieldName = field.getName();
            Double fieldValue = null;
            try {
                fieldValue = (Double) field.get(this);
            } catch (IllegalAccessException e) {
                emptyFields.put(fieldName, String.format("%-" + parentWidth + "s%n", ""));
            } finally {
                if (fieldValue != null) {
                    if (fieldName.equals("dmg_taken")) {
                        setFields.add(getColoredString(fieldName, fieldValue, parentWidth, true));
                    } else {
                        setFields.add(getColoredString(fieldName, fieldValue, parentWidth));
                    }
                } else {
                    emptyFields.put(fieldName, String.format("%-" + parentWidth + "s%n", ""));
                }
            }
        }

        StringBuilder result = new StringBuilder();

        setFields.forEach(result::append);
        emptyFields.forEach((fieldName, string) -> result.append(string));

        return result.toString();
    }
    
    private String getColoredString(String valueName, Double value, int parentWidth) {
        return getColoredString(valueName, value, parentWidth, false);
    }

    private String getColoredString(String valueName, Double value, int parentWidth, boolean invert) {
        if (value == null) {
            return String.format("%-" + parentWidth + "s%n", "");
        }

        if ((value < 0 || value < 1) && !invert) {
            return getNerfString(valueName, value, parentWidth);
        }
        return getBuffString(valueName, value, parentWidth);
    }

    private String getNerfString(String valueName, Double value, int parentWidth) {
        if (value == null) {
            return String.format("%-" + parentWidth + "s%n", "");
        }
        int valueWidth = parentWidth - ATTRIBUTE_MIN_WIDTH - 5;
        return String.format("   %-" + ATTRIBUTE_MIN_WIDTH + "s: @|red %-" + valueWidth + "s|@%n", valueName, value);
    }

    private String getBuffString(String valueName, Double value, int parentWidth) {
        if (value == null) {
            return String.format("%-" + parentWidth + "s%n", "");
        }
        int valueWidth = parentWidth - ATTRIBUTE_MIN_WIDTH - 5;
        return String.format("   %-" + ATTRIBUTE_MIN_WIDTH + "s: @|green %-" + valueWidth + "s|@%n", valueName, value);
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
