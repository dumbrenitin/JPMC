package com.jpmc.market.database;

import com.jpmc.market.entity.Trade;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TradeMarketData {

  private ConcurrentHashMap<String, List<Trade>> tradeMarketMap;

  /*
   * This map will hold the trade related data
   */
  @PostConstruct
  private void initialize() {
    if (tradeMarketMap == null) {
      tradeMarketMap = new ConcurrentHashMap<>();
    }
  }

  public ConcurrentHashMap<String, List<Trade>> getTradeMarketMap() {
    if (tradeMarketMap == null) {
      initialize();
    }
    return tradeMarketMap;
  }
}
