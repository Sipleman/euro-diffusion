package com.company.mediator;

import com.company.model.events.Event;

public interface Mediator {
  void notify(Event event);
}
