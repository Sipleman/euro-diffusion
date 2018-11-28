package com.company.model;

import com.company.model.events.CompleteEvent;
import com.company.mediator.Component;
import com.company.mediator.Mediator;

import java.util.ArrayList;
import java.util.List;

public class Country extends Component {
  private String name;
  private List<City> cities = new ArrayList<>();
  private int lx, ly, rx, ry;
  private int countOfCountries = 0;
  private int completion = 0;

  public int getCountOfCountries() {
    return countOfCountries;
  }

  public void setCountOfCountries(int countOfCountries) {
    this.countOfCountries = countOfCountries;
  }

  public int getLx() {
    return lx;
  }

  public void setLx(int lx) {
    this.lx = lx;
  }

  public int getLy() {
    return ly;
  }

  public void setLy(int ly) {
    this.ly = ly;
  }

  public int getRx() {
    return rx;
  }

  public void setRx(int rx) {
    this.rx = rx;
  }

  public int getRy() {
    return ry;
  }

  public void setRy(int ry) {
    this.ry = ry;
  }

  public Country(String name, int lx, int ly, int rx, int ry) {
    this.name = name;
    this.lx = lx;
    this.ly = ly;
    this.rx = rx;
    this.ry = ry;
  }

  public Country(String name, Mediator dialog) {
    super(dialog);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addCity(int x, int y) {
    cities.add(new City(dialog, x, y, this));
  }

  public void countOneDay() {
    for (City city : cities) {
      city.countOneDay();
    }
  }
  public void refresh() {
    for(City city : cities) {
      city.refreshBalance();
    }
  }

  public void onComplete() {
    completion++;
    if (isComplete()) {
      dialog.notify(new CompleteEvent(this));
    }
  }

  public City getLast() {
    return cities.get(cities.size()-1);
  }

  public List<City> getCities() {
    return cities;
  }

  public boolean isComplete() {
    return completion == cities.size();
  }

}
