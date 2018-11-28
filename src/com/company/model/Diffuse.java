package com.company.model;

import com.company.model.events.CompleteEvent;
import com.company.model.events.Event;
import com.company.model.events.SendEvent;
import com.company.mediator.Mediator;
import com.company.utils.CoinMap;

import java.util.HashMap;
import java.util.Map;

public class Diffuse implements Mediator {

  private Map<String, Country> countries = new HashMap<String, Country>();

  private City matrix[][];

  private boolean isComplete = false;

  public boolean isComplete() {
    return isComplete;
  }

  int days = 0;

  public Diffuse() {
    matrix = new City[10][10];
  }

  public void addCountry(Country country) {
    country.setDialog(this);
    for (int y = country.getLy(); y <= country.getRy(); y++) {
      for (int x = country.getLx(); x <= country.getRx(); x++) {
        country.addCity(x, y);
        matrix[x][y] = country.getLast();
      }
    }
    countries.put(country.getName(), country);
  }

  public void countOneDay() {
    performCountries();
    doTransactions();
    days++;
  }

  public void initNeighbors() {
    for (Country country : countries.values()) {
      for (City city : country.getCities()) {
        fillWithNeighbors(city);
        country.setCountOfCountries(countries.size());
      }
    }
  }

  private void performCountries() {
    for (Country country : countries.values()) {
      country.countOneDay();
    }
  }

  private void doTransactions() {
    for (Country country : countries.values()) {
      country.refresh();
    }
  }

  private void fillWithNeighbors(City city) {
    int x = city.getX();
    int y = city.getY();
    if (checkLeft(x, y))
      city.addNeighbour(matrix[x - 1][y]);
    if (checkRight(x, y))
      city.addNeighbour(matrix[x + 1][y]);
    if (checkHigh(x, y))
      city.addNeighbour(matrix[x][y - 1]);
    if (checkDown(x, y))
      city.addNeighbour(matrix[x][y + 1]);
  }

  private boolean checkLeft(int x, int y) {
    return x != 0 && matrix[x - 1][y] != null;
  }

  private boolean checkRight(int x, int y) {
    return x != 9 && matrix[x + 1][y] != null;
  }

  private boolean checkHigh(int x, int y) {
    return y != 0 && matrix[x][y - 1] != null;
  }

  private boolean checkDown(int x, int y) {
    return y != 9 && matrix[x][y + 1] != null;
  }

  @Override
  public void notify(Event event) {
    if (event instanceof SendEvent) {
      SendEvent sendEvent = (SendEvent) event;
      CoinMap coins = sendEvent.getValue();
      sendEvent.getCity().withdrawCoins(coins);
      sendEvent.getTo().acceptCoins(coins);
    }
    if (event instanceof CompleteEvent) {
      System.out.println(
          ((CompleteEvent) event).getCountry().getName() + " " + days);
      if (isAllCountriesComplete())
        isComplete = true;
    }
  }

  private boolean isAllCountriesComplete() {
    for (Country country : countries.values()) {
      if (!country.isComplete())
        return false;
    }
    return true;
  }

}
