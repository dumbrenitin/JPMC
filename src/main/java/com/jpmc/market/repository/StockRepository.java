package com.jpmc.market.repository;

import com.jpmc.market.entity.Trade;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface StockRepository<String, Stock> {

  ConcurrentHashMap<String, Stock> getAllStock();

  Stock getStockBySymbol(String key);

  List<Trade> getTradesbyStock(String key);

  ConcurrentHashMap<java.lang.String, List<Trade>> getAllTrades();

  void create(java.lang.String symbol, Trade trade);
}
