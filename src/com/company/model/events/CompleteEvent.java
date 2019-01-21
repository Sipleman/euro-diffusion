package com.company.model.events;

import com.company.model.Country;

public class CompleteEvent extends Event {
  Country country;

  public CompleteEvent(Country country) {
    this.country = country;
  }

  public Country getCountry() {
    return country;
  }

}
