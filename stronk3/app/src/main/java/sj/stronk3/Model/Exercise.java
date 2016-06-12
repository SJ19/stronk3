package sj.stronk3.Model;

/**
 * Created by Corsair on 9-6-2016.
 */
public class Exercise {
    private int id;
    private int sets;
    private int repetitions;
    private String name;

    public Exercise(int id, int sets, int repetitions, String name) {
        this.id = id;
        this.sets = sets;
        this.repetitions = repetitions;
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
}
