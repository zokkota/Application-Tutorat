package oop;

public enum Motivation {
    MOTIVATED('A', .9), NEUTRAL('B', 1), NOT_THAT_MUCH('C', 1.1);

    private char abbr;
    private double weightModifier;

    private Motivation(char abbr, double weightModifier) {
        this.abbr = abbr;
        this.weightModifier = weightModifier;
    }

    public static Motivation getMotivationByAbbr(char abbr) {
        for (Motivation m : Motivation.values()) {
            if (m.abbr == abbr) {
                return m;
            }
        }
        return null;
    }

    public double getWeightModifier() {
        return weightModifier;
    }

    public char getAbbr() {
        return abbr;
    }
}
