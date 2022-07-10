package com.jpmc.market.domain;

import com.jpmc.market.entity.TradeType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class StockTradeRequest {
  @NotNull private String symbol;

  @NotNull private TradeType type;

  @NotNull private Double tradePrice;

  @NotNull
  @Min(1)
  private Long sharesQuantity;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public TradeType getType() {
    return type;
  }

  public void setType(TradeType type) {
    this.type = type;
  }

  public Double getTradePrice() {
    return tradePrice;
  }

  public void setTradePrice(Double tradePrice) {
    this.tradePrice = tradePrice;
  }

  public Long getSharesQuantity() {
    return sharesQuantity;
  }

  public void setSharesQuantity(Long sharesQuantity) {
    this.sharesQuantity = sharesQuantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StockTradeRequest that = (StockTradeRequest) o;
    return symbol.equals(that.symbol)
        && type == that.type
        && tradePrice.equals(that.tradePrice)
        && sharesQuantity.equals(that.sharesQuantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(symbol, type, tradePrice, sharesQuantity);
  }

  @Override
  public String toString() {
    return "StockTradeRequest{"
        + "symbol='"
        + symbol
        + '\''
        + ", type="
        + type
        + ", tradePrice="
        + tradePrice
        + ", sharesQuantity="
        + sharesQuantity
        + '}';
  }
}
