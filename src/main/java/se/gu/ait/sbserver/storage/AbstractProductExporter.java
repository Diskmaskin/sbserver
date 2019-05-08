package se.gu.ait.sbserver.storage;

import se.gu.ait.sbserver.domain.Product;

public abstract class AbstractProductExporter implements Product.Exporter {
  protected String name;
  protected double price;
  protected double alcohol;
  protected int volume;
  protected int nr;
  protected String productGroup;
  protected String type;
  protected String added;
  protected int dropped;

  @Override
  public void addName(String name) {
    this.name = name;
  }

  @Override
  public void addPrice(double price) {
    this.price = price;
  }

  @Override
  public void addAlcohol(double alcohol) {
    this.alcohol = alcohol;
  }

  @Override
  public void addVolume(int volume) {
    this.volume = volume;
  }

  @Override
  public void addNr(int nr) {
    this.nr = nr;
  }

  @Override
  public void addProductGroup(String productGroup) {
    this.productGroup = productGroup;
  }

  @Override
  public void addType(String type) {
    this.type = type;
  }

  @Override
  public void addAdded(String added) {
    this.added = added;
  }

  @Override
  public void addDropped(int dropped) {
    this.dropped = dropped;
  }

}
