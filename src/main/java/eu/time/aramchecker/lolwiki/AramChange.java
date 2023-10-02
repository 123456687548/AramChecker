package eu.time.aramchecker.lolwiki;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AramChange {
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

    @Override
    public String toString() {
        Class<?> clazz = this.getClass();

        Field[] declaredFields = clazz.getFields();

        Map<String, String> emptyFields = new HashMap<>();
        List<String> setFields = new ArrayList<>();

        for (Field field : declaredFields) {
            String fieldName = field.getName();
            Double fieldValue = null;
            try {
                fieldValue = (Double)field.get(this);
            } catch (IllegalAccessException e) {
                emptyFields.put(fieldName, String.format("%-26s%n", ""));
            } finally {
                if (fieldValue != null) {
                    if (fieldName.equals("dmg_taken")) {
                        setFields.add(getColoredString(fieldName, fieldValue, true));
                    } else {
                        setFields.add(getColoredString(fieldName, fieldValue));
                    }
                } else {
                    emptyFields.put(fieldName, String.format("%-26s%n", ""));
                }
            }
        }

        StringBuilder result = new StringBuilder();

        setFields.forEach(result::append);
        emptyFields.forEach((fieldName, string) -> result.append(string));

        return result.toString();
    }

    private String getColoredString(String valueName, Double value) {
        return getColoredString(valueName, value, false);
    }

    private String getColoredString(String valueName, Double value, boolean invert) {
        if (value == null) {
            return String.format("%-26s%n", "");
        }

        if ((value < 0 || value < 1) && !invert) {
            return getNerfString(valueName, value);
        }
        return getBuffString(valueName, value);
    }

    private String getNerfString(String valueName, Double value) {
        if (value == null) {
            return String.format("%-24s%n", "");
        }
        return String.format("%-14s: @|red %-10s|@%n", valueName, value);
    }

    private String getBuffString(String valueName, Double value) {
        if (value == null) {
            return String.format("%-24s", "");
        }
        return String.format("%-14s: @|green %-10s|@%n", valueName, value);
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
