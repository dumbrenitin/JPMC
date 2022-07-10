package com.jpmc.market.domain;

public class PERatioResponse {
  private String symbol;

  private Double stockPrice;

  private Double peRatio;

  private PERatioResponse(PERatioResponseBuilder builder) {
    this.symbol = builder.symbol;
    this.stockPrice = builder.stockPrice;
    this.peRatio = builder.peRatio;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getStockPrice() {
    return stockPrice;
  }

  public void setStockPrice(Double stockPrice) {
    this.stockPrice = stockPrice;
  }

  public Double getPeRatio() {
    return peRatio;
  }

  public void setgetPeRatio(Double peRatio) {
    this.peRatio = peRatio;
  }

  @Override
  public String toString() {
    return "PERatioResponse{"
        + "symbol='"
        + symbol
        + '\''
        + ", stockPrice="
        + stockPrice
        + ", peRatio="
        + peRatio
        + '}';
  }

  public static class PERatioResponseBuilder {
    private String symbol;
    private Double stockPrice;
    private Double peRatio;

    public PERatioResponseBuilder() {}

    public PERatioResponseBuilder symbol(final String symbol) {
      this.symbol = symbol;
      return this;
    }

    public PERatioResponseBuilder stockPrice(final Double stockPrice) {
      this.stockPrice = stockPrice;
      return this;
    }

    public PERatioResponseBuilder peRatio(final Double peRatio) {
      this.peRatio = peRatio;
      return this;
    }

    public PERatioResponse build() {
      return new PERatioResponse(this);
    }
  }
}
