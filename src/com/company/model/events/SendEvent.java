package com.company.model.events;

import com.company.model.City;
import com.company.utils.model.CoinMap;

public class SendEvent extends Event{
  private City city;
  private CoinMap value;
  private City to;

  public City getTo() {
    return to;
  }

  public void setTo(City to) {
    this.to = to;
  }

  public SendEvent(City city, CoinMap coinMap, City to){
    this.city = city;
    this.value = coinMap;
    this.to = to;
  }

  public City getCity() {
    return city;
  }

  public CoinMap getValue() {
    return value;
  }
}
