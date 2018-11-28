package com.company.model;

import com.company.model.events.SendEvent;
import com.company.mediator.Component;
import com.company.mediator.Mediator;
import com.company.utils.CoinMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class City extends Component {
  private static final int COEFFICIENT = 1000;
  private int x;
  private int y;
  private Country country;
  private CoinMap currentBalance;
  private CoinMap moneyToCome;
  private CoinMap moneyToWithdraw;

  private List<City> neighbours = new ArrayList<City>();

  void addNeighbour(City city) {
    neighbours.add(city);
  }

  private boolean isComplete = false;

  public String getCountryName() {
    return country.getName();
  }

  City(Mediator dialog, int x, int y,
      Country country) {
    super(dialog);
    this.x = x;
    this.y = y;
    this.country = country;
    currentBalance = CoinMap.createDefaultCoinMap(country);
    moneyToCome = new CoinMap();
    moneyToWithdraw = new CoinMap();
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    City city = (City) o;
    return x == city.x &&
        y == city.y &&
        Objects.equals(country, city.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, country);
  }

  int getAllCoins() {
    int sum = 0;
    for (Country country : currentBalance.keySet()) {
      sum += currentBalance.get(country);
    }
    return sum;
  }

  void withdrawCoins(CoinMap coins) {
    for (Country country : coins.keySet()) {
      moneyToWithdraw.addCoins(country, coins.get(country));
    }
  }

  void countOneDay() {
    for (City city : neighbours) {
      sendCoins(city);
    }

    if (!isComplete && isComplete()) {
      isComplete = true;
      sendCompleteEvent();
    }
  }

  void acceptCoins(Map<Country, Integer> coins) {
    for (Country country : coins.keySet()) {
      moneyToCome.addCoins(country, coins.get(country));
    }
  }

  void refreshBalance() {
    for (Country country : moneyToWithdraw.keySet()) {
      currentBalance.subtractCoins(country, moneyToWithdraw.get(country));
    }
    moneyToWithdraw = new CoinMap();
    for (Country country : moneyToCome.keySet()) {
      currentBalance.addCoins(country, moneyToCome.get(country));
    }
    moneyToCome = new CoinMap();
  }

  private void sendCoins(City city) {
    CoinMap coinMapToSend = getCoinMapToSend();
    dialog.notify(
        new SendEvent(this, coinMapToSend, city));
  }

  private void sendCompleteEvent() {
    country.onComplete();
  }

  private CoinMap getCoinMapToSend() {
    CoinMap coinMap = new CoinMap();
    for (Country country : currentBalance.keySet()) {
      if (currentBalance.get(country) / COEFFICIENT >= 1)
        coinMap.put(country, currentBalance.get(country) / COEFFICIENT);
    }
    return coinMap;
  }

  private boolean isComplete() {
    return currentBalance.keySet().size() == country.getCountOfCountries();
  }
}
