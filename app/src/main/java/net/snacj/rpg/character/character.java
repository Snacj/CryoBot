package net.snacj.rpg.character;

/**
 * This class represents a character in the RPG system.
 */
public class character {
    private String name;
    private int age;
    private String species;
    private String rank;
    private String profession;
    private String shipAssignment;
    private String location;

    /**
     * @param name
     * @param age
     * @param species
     * @param rank
     * @param profession
     * @param shipAssignment
     * @param location
     */
    public character(String name, int age, String species, String rank, String profession, String shipAssignment, String location) {
        this.name = name;
        this.age = age;
        this.species = species;
        this.rank = rank;
        this.profession = profession;
        this.shipAssignment = shipAssignment;
        this.location = location;
    }
}
