package com.jpmc.market.service;

import com.jpmc.market.entity.Stock;
import com.jpmc.market.entity.Trade;
import com.jpmc.market.domain.StockTradeRequest;

import java.util.List;

public interface StockService {
  Double calculateDividendYield(String stockSymbol, Double stockPrice);

  Stock getStock(String stockSymbol);

  Double calculatePERatio(String symbol, Double stockPrice);

  Double calculateVolWeightedPice(String symbol);

  List<Trade> getlastMinutesTrades(String key, int minutes);

  void saveTrade(StockTradeRequest tradeRequest);

  Double calculateGBCE();
}
