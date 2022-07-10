package com.jpmc.market.database;

import com.jpmc.market.entity.Stock;
import com.jpmc.market.entity.StockType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StockMarketData {

  @Value("${jpmc.database.stock.symbol}")
  private String stockSymbols;

  @Value("${jpmc.database.stock.type}")
  private String stockTypes;

  @Value("${jpmc.database.stock.lastDividend}")
  private String lastDividends;

  @Value("${jpmc.database.stock.fixedDividend}")
  private String fixedDividend;

  @Value("${jpmc.database.stock.parValue}")
  private String parValue;

  private ConcurrentHashMap<String, Stock> stockMarketMap;

  /*
   * This will initialize the stock market data into hashMap
   */
  @PostConstruct
  private void initialize() {
    if (stockMarketMap == null) {

      String[] symbols = null;

      if (StringUtils.isNotEmpty(stockSymbols)) {
        symbols = stockSymbols.split(",");
      }

      stockMarketMap = new ConcurrentHashMap<>();
      String[] types = stockTypes.split(",");
      String[] lastDividends = this.lastDividends.split(",");
      String[] fixedDividends = fixedDividend.split(",");
      String[] parValues = parValue.split(",");

      for (int i = 0; i < symbols.length; i++) {
        Stock stock =
            new Stock.Builder()
                .symbol(symbols[i])
                .type(StockType.valueOf(types[i]))
                .lastDividend(Double.valueOf(lastDividends[i]))
                .fixedDividend(Double.valueOf(fixedDividends[i]))
                .parValue(Double.valueOf(parValues[i]))
                .build();

        stockMarketMap.put(symbols[i], stock);
      }
    }
  }

  public ConcurrentHashMap<String, Stock> getStockMarketMap() {
    if (stockMarketMap == null) {
      initialize();
    }

    return stockMarketMap;
  }
}
