package com.jpmc.market.domain;

public class VolWeightedResponse {
  private String symbol;

  private Double volWeighedPrice;

  private VolWeightedResponse(VolWeightedResponseBuilder builder) {
    this.symbol = builder.symbol;
    this.volWeighedPrice = builder.volWeighedPrice;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getVolWeighedPrice() {
    return volWeighedPrice;
  }

  public void setVolWeighedPrice(Double volWeighedPrice) {
    this.volWeighedPrice = volWeighedPrice;
  }

  @Override
  public String toString() {
    return "VolWeightedResponse{"
        + "symbol='"
        + symbol
        + '\''
        + ", volWeighedPrice="
        + volWeighedPrice
        + '}';
  }

  public static class VolWeightedResponseBuilder {
    private String symbol;
    private Double volWeighedPrice;

    public VolWeightedResponseBuilder() {}

    public VolWeightedResponseBuilder symbol(final String symbol) {
      this.symbol = symbol;
      return this;
    }

    public VolWeightedResponseBuilder volWeighedPrice(final Double volWeighedPrice) {
      this.volWeighedPrice = volWeighedPrice;
      return this;
    }

    public VolWeightedResponse build() {
      return new VolWeightedResponse(this);
    }
  }
}
