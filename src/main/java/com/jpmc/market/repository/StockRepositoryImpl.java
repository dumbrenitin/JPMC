package com.jpmc.market.repository;

import com.jpmc.market.database.StockMarketData;
import com.jpmc.market.database.TradeMarketData;
import com.jpmc.market.entity.Stock;
import com.jpmc.market.entity.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StockRepositoryImpl implements StockRepository<String, Stock> {

  @Autowired private StockMarketData stockMarketData;

  @Autowired private TradeMarketData tradeMarketData;

  @Override
  public ConcurrentHashMap<String, Stock> getAllStock() {
    return stockMarketData.getStockMarketMap();
  }

  @Override
  public Stock getStockBySymbol(String key) {
    return getAllStock().get(key);
  }

  public void create(java.lang.String symbol, Trade trade) {
    List<Trade> trades = getTradesbyStock(symbol);
    if (trades == null) {
      trades = new ArrayList<>();
    }
    trades.add(trade);
    tradeMarketData.getTradeMarketMap().put(symbol, trades);
  }

  public List<Trade> getTradesbyStock(String key) {
    return getAllTrades().get(key);
  }

  @Override
  public ConcurrentHashMap<String, List<Trade>> getAllTrades() {
    return tradeMarketData.getTradeMarketMap();
  }
}
