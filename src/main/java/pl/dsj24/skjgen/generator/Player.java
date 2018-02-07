package pl.dsj24.skjgen.generator;

import java.util.Arrays;

public class Player {

    private String name;
    private String country;
    private boolean randomSuit;
    private String[] colors;

    public Player(String name, String country, boolean randomSuit, String[] colors) {
        this.name = name;
        this.country = country.toLowerCase();
        this.randomSuit = randomSuit;
        this.colors = colors;
    }

    public boolean isRandomSuit() {
        return randomSuit;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String[] getColors() {
        return colors;
    }

    @Override
    public String toString() {
        return "[Player] " + getName() + " " + getCountry() + " " + isRandomSuit() + " " + Arrays.toString(getColors());
    }
}
