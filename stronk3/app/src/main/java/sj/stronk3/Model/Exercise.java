package sj.stronk3.Model;

/**
 * Created by Corsair on 9-6-2016.
 */
public class Exercise {
    private int id;
    private int sets;
    private int repetitions;
    private double weight;
    private String name;

    public Exercise(int id, int sets, int repetitions, double weight, String name) {
        this.id = id;
        this.sets = sets;
        this.repetitions = repetitions;
        this.weight = weight;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getSets() {
        return sets;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public String getName() {
        return name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
