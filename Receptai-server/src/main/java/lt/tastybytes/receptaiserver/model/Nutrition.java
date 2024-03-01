package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;

@Entity
public class Nutrition {

    @Id
    private long id;

    private String unit;

    private double fat;

    private double protein;

    private double carbohydrates;

    private double sugars;

    private double fiber;

    private double sodium;
}
