package net.snacj.rpg.character;

/**
 * This class represents a character in the RPG system.
 */
public class character {
    private final String race;
    private final String klasse;
    private final int age;

    /**
     * This constructor creates a character with the
     * specified age
     * @param age
     * @param race
     * @param klasse
     */
    public character(int age, String race, String klasse) {
        this.age = age;
        this.race = race;
        this.klasse = klasse;
    }

    public void attack () {

    }

    public void defend () {

    }
}
