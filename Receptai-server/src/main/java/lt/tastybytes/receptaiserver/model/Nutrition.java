package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;

@Entity
public class Nutrition{

  private long id;

  private String unit;

  private double fat;

  private double protein;

  private double carbohydrates;

  private double sugars;

  private double fiber;

  private double sodium;

  public void setId(long id) {
        this.id = id;
    }

  public void setUnit(String unit) {
      this.unit = unit;
  }

  public void setFat(double fat) {
      this.fat = fat;
  }

  public void setProtein(double protein) {
      this.protein = protein;
  }

  public void setCarbohydrates(double carbohydrates) {
      this.carbohydrates = carbohydrates;
    }

  public void setSugar(double sugar) {
      this.sugar = sugar;
  }

  public void setFiber(double fiber) {
      this.fiber = fiber;
  }

  public void setSodium(double sodium) {
      this.sodium = sodium;
  }

  public long getId() {
      return id;
  }

  public String getUnit() {
      return unit;
  }

  public double getFat() {
      return fat;
  }

  public double getProtein() {
      return protein;
  }

  public double getCarbohydrates() {
      return carbohydrates;
  }

  public double getSugar() {
      return sugar;
  }

  public double getFiber() {
      return fiber;
  }

  public double getSodium() {
      return sodium;
  }
}
