package com.jpmc.market.entity;

import java.util.Objects;

public class Stock {
  private long id;
  private String symbol;
  private StockType type;
  private Double lastDividend;
  private Double fixedDividend;
  private Double parValue;

  private Stock(Builder builder) {
    this.id = builder.id;
    this.symbol = builder.symbol;
    this.type = builder.type;
    this.lastDividend = builder.lastDividend;
    this.fixedDividend = builder.fixedDividend;
    this.parValue = builder.parValue;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public StockType getType() {
    return type;
  }

  public void setType(StockType type) {
    this.type = type;
  }

  public Double getLastDividend() {
    return lastDividend;
  }

  public void setLastDividend(Double lastDividend) {
    this.lastDividend = lastDividend;
  }

  public Double getFixedDividend() {
    return fixedDividend;
  }

  public void setFixedDividend(Double fixedDividend) {
    this.fixedDividend = fixedDividend;
  }

  public Double getParValue() {
    return parValue;
  }

  public void setParValue(Double parValue) {
    this.parValue = parValue;
  }

  @Override
  public String toString() {
    return "Stock{"
        + "id="
        + id
        + ", symbol='"
        + symbol
        + '\''
        + ", type="
        + type
        + ", lastDividend="
        + lastDividend
        + ", fixedDividend="
        + fixedDividend
        + ", parValue="
        + parValue
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Stock stock = (Stock) o;
    return symbol.equals(stock.symbol)
        && type == stock.type
        && lastDividend.equals(stock.lastDividend)
        && fixedDividend.equals(stock.fixedDividend)
        && parValue.equals(stock.parValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(symbol, type, lastDividend, fixedDividend, parValue);
  }

  public static class Builder {
    private long id;
    private String symbol;
    private StockType type;
    private Double lastDividend;
    private Double fixedDividend;
    private Double parValue;

    public Builder() {}

    public Builder symbol(final String symbol) {
      this.symbol = symbol;
      return this;
    }

    public Builder type(final StockType type) {
      this.type = type;
      return this;
    }

    public Builder lastDividend(final Double lastDividend) {
      this.lastDividend = lastDividend;
      return this;
    }

    public Builder fixedDividend(final Double fixedDividend) {
      this.fixedDividend = fixedDividend;
      return this;
    }

    public Builder parValue(final Double parValue) {
      this.parValue = parValue;
      return this;
    }

    public Stock build() {
      return new Stock(this);
    }
  }
}
