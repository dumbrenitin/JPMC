package com.jpmc.market.entity;

import java.util.Date;
import java.util.Objects;

public class Trade {
  private final String symbol;
  private final TradeType type;
  private final Double tradedPrice;
  private final Long sharesQuantity;
  private final Date timestamp;

  private Trade(TradeBuilder builder) {
    this.symbol = builder.symbol;
    this.type = builder.type;
    this.tradedPrice = builder.tradedPrice;
    this.sharesQuantity = builder.sharesQuantity;
    this.timestamp = builder.timestamp;
  }

  public TradeType getType() {
    return type;
  }

  public Double getTradedPrice() {
    return tradedPrice;
  }

  public Long getSharesQuantity() {
    return sharesQuantity;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Trade trade = (Trade) o;
    return symbol.equals(trade.symbol)
        && type == trade.type
        && tradedPrice.equals(trade.tradedPrice)
        && sharesQuantity.equals(trade.sharesQuantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(symbol, type, tradedPrice, sharesQuantity);
  }

  @Override
  public String toString() {
    return "Trade{"
        + "symbol='"
        + symbol
        + '\''
        + ", type="
        + type
        + ", tradedPrice="
        + tradedPrice
        + ", sharesQuantity="
        + sharesQuantity
        + ", timestamp="
        + timestamp
        + '}';
  }

  public static class TradeBuilder {
    private String symbol;
    private TradeType type;
    private Double tradedPrice;
    private Long sharesQuantity;
    private Date timestamp;

    public TradeBuilder symbol(final String symbol) {
      this.symbol = symbol;
      return this;
    }

    public TradeBuilder type(final TradeType type) {
      this.type = type;
      return this;
    }

    public TradeBuilder tradedPrice(final Double tradedPrice) {
      this.tradedPrice = tradedPrice;
      return this;
    }

    public TradeBuilder sharesQuantity(final Long sharesQuantity) {
      this.sharesQuantity = sharesQuantity;
      return this;
    }

    public TradeBuilder timestamp(final Date timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Trade build() {
      return new Trade(this);
    }
  }
}
