package eu.time.aramchecker.lolwiki;

public class AramChange {
    public double id;
    public String name;
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
        return
            getColoredString("ability_haste", ability_haste) +
                getColoredString("dmg_dealt", dmg_dealt) +
                getColoredString("dmg_taken", dmg_taken, true) +
                getColoredString("healing", healing) +
                getColoredString("shielding", shielding) +
                getColoredString("tenacity", tenacity) +
                getColoredString("energy_regen", energy_regen) +
                getColoredString("attack_speed", attack_speed);
    }

    private String getColoredString(String valueName, Double value) {
        return getColoredString(valueName, value, false);
    }

    private String getColoredString(String valueName, Double value, boolean invert) {
        if (value == null) {
            return "";
        }

        if ((value < 0 || value < 1) && !invert) {
            return getNerfString(valueName, value);
        }
        return getBuffString(valueName, value);
    }

    private String getNerfString(String valueName, Double value) {
        if (value == null) {
            return "";
        }
        return String.format("%-14s: @|red %-10s|@%n", valueName, value);
    }

    private String getBuffString(String valueName, Double value) {
        if (value == null) {
            return "";
        }
        return String.format("%-14s: @|green %-10s|@%n", valueName, value);
    }
}
