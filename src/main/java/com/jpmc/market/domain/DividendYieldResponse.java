package com.jpmc.market.domain;

public class DividendYieldResponse {
  private String symbol;

  private Double stockPrice;

  private Double dividendYield;

  private DividendYieldResponse(DividendYieldResponseBuilder builder) {
    this.symbol = builder.symbol;
    this.stockPrice = builder.stockPrice;
    this.dividendYield = builder.dividendYield;
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

  public Double getDividendYield() {
    return dividendYield;
  }

  public void setDividendYield(Double dividendYield) {
    this.dividendYield = dividendYield;
  }

  @Override
  public String toString() {
    return "DividendYieldResponse{"
        + "symbol='"
        + symbol
        + '\''
        + ", stockPrice="
        + stockPrice
        + ", dividendYield="
        + dividendYield
        + '}';
  }

  public static class DividendYieldResponseBuilder {
    private String symbol;
    private Double stockPrice;
    private Double dividendYield;

    public DividendYieldResponseBuilder() {}

    public DividendYieldResponseBuilder symbol(final String symbol) {
      this.symbol = symbol;
      return this;
    }

    public DividendYieldResponseBuilder stockPrice(final Double stockPrice) {
      this.stockPrice = stockPrice;
      return this;
    }

    public DividendYieldResponseBuilder dividendYield(final Double dividendYield) {
      this.dividendYield = dividendYield;
      return this;
    }

    public DividendYieldResponse build() {
      return new DividendYieldResponse(this);
    }
  }
}
