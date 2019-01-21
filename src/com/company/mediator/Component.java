package com.company.mediator;

public class Component {
  protected Mediator dialog;

  public Component(Mediator dialog) {
    this.dialog = dialog;
  }

  public Component() {}

  public void setDialog(Mediator dialog) {
    this.dialog = dialog;
  }
}
