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


  public int getY() {
    return y;
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
      country.onComplete();
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
