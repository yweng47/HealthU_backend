package edu.uwo.health.entity;

public enum HealthCode {
    GREEN(0), YELLOW(1), RED(2);

    private final int value;
    private HealthCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
