package com.company.utils.model;

import com.company.model.Country;

import java.util.HashMap;

public class CoinMap extends HashMap<Country, Integer> {

  private static final int INITIAL_COUNT = 1000000;

  public CoinMap() {
  }

  public CoinMap(Country country, int value) {
    this.put(country, value);
  }

  public static CoinMap createDefaultCoinMap(Country country) {
    return new CoinMap(country, INITIAL_COUNT);
  }

  public void addCoins(Country country, int value) {
    if (!this.containsKey(country))
      this.put(country, value);
    else {
      int newValue = this.get(country) + value;
      this.replace(country, newValue);
    }
  }

  public void subtractCoins(Country country, int value) {
    if (!this.containsKey(country))
      this.put(country, value);
    else {
      int newValue = this.get(country) - value;
      this.replace(country, newValue);
    }
  }

}
