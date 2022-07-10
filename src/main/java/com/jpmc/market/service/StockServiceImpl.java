package com.jpmc.market.service;

import com.jpmc.market.entity.Stock;
import com.jpmc.market.entity.StockType;
import com.jpmc.market.entity.Trade;
import com.jpmc.market.domain.StockTradeRequest;
import com.jpmc.market.exception.StockNotFoundException;
import com.jpmc.market.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {
  private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

  @Value("${jpmc.minutes.for.last.trades}")
  private Integer minutes;

  @Autowired private StockRepository stockRepository;

  /**
   * This method will calculate the dividend yield for given Stock and price based on the stock type
   *
   * @param stockPrice- price of the given stock
   * @param stockSymbol- symbol of the stock
   * @return the calculated dividend Yield
   */
  @Override
  public Double calculateDividendYield(String stockSymbol, Double stockPrice) {
    log.debug("Entering into StockServiceImpl::calculateDividendYield()");
    Stock stock = getStock(stockSymbol);
    Double dividendYield = null;
    if (StockType.Common == stock.getType()) {
      dividendYield = stock.getLastDividend() / stockPrice;
    } else {
      dividendYield = (stock.getFixedDividend() * stock.getParValue()) / stockPrice;
    }

    log.debug(
        "Div Yield for given Stock {} and Price {} is : {}",
        stockSymbol,
        stockPrice,
        dividendYield);
    log.debug("Existing into StockServiceImpl::calculateDividendYield()");
    return dividendYield;
  }

  /**
   * This method will return the Stock object based on the provided stock symbol
   *
   * @param stockSymbol- symbol of the stock
   * @return {Stock} object
   */
  @Override
  public Stock getStock(String stockSymbol) {
    log.debug("Entering into StockServiceImpl::getStock()");
    Stock stock = (Stock) stockRepository.getStockBySymbol(stockSymbol);

    if (stock == null) {
      log.error("Stock {} is not exists", stockSymbol);
      throw new StockNotFoundException("Stock [" + stockSymbol + "] is invalid");
    }
    log.debug("Found Stock[{}] with Symbol {} ", stock, stockSymbol);
    log.debug("Entering into StockServiceImpl::getStock()");
    return stock;
  }

  /**
   * This method will calculate the PE ratio for given Stock and price
   *
   * @param stockPrice- price of the given stock
   * @param symbol- symbol of the stock
   * @return the calculated PE ratio
   */
  @Override
  public Double calculatePERatio(String symbol, Double stockPrice) {
    log.debug("Entering into StockServiceImpl::calculatePERatio()");
    Double dividendYield = calculateDividendYield(symbol, stockPrice);
    Double peRatio = stockPrice / dividendYield;
    log.debug("PE Ratio for given Stock {} and Price {} is : {}", symbol, stockPrice, peRatio);
    log.debug("Entering into StockServiceImpl::calculatePERatio()");
    return peRatio;
  }

  /**
   * This method will calculate Volume Weighted Stock Price based on trades in past {} minutes for
   * given stock
   *
   * @param symbol- symbol of the stock
   * @return the calculated Volume Weighted Stock Price
   */
  @Override
  public Double calculateVolWeightedPice(String symbol) {
    log.debug("Entering into StockServiceImpl::calculateVolWeightedPice()");
    getStock(symbol);

    List<Trade> trades = getlastMinutesTrades(symbol, minutes);
    Double volWeightedPrice = 0d;
    Long quantity = 0l;
    if (trades != null) {
      for (Trade trade : trades) {
        quantity += trade.getSharesQuantity();
        volWeightedPrice += trade.getSharesQuantity() * trade.getTradedPrice();
      }
      volWeightedPrice /= quantity;
    }
    log.debug("Vol Weighted Price for given stock {} is : {}", symbol, volWeightedPrice);
    log.debug("Entering into StockServiceImpl::calculateVolWeightedPice()");
    return volWeightedPrice;
  }

  /**
   * This method will return all the trades which are executed in past {} minutes for given stock
   *
   * @param stockSymbol- symbol of the stock
   * @param minutes- n of minutes to get the trades
   * @return :List of the trades are execuated in past {Minutes}
   */
  @Override
  public List<Trade> getlastMinutesTrades(String stockSymbol, int minutes) {
    log.debug("Entering into StockServiceImpl::getlastMinutesTrades()");
    Date date = new Date();
    long time = date.getTime() - (minutes * 60 * 1000);
    List<Trade> listTrades = stockRepository.getTradesbyStock(stockSymbol);
    List<Trade> tradesInLastMinutes = null;

    if (listTrades != null)
      tradesInLastMinutes =
          listTrades.stream()
              .filter(trade -> trade.getTimestamp().getTime() >= time)
              .collect(Collectors.toList());
    log.debug(
        "Last {} Trades for given stocks {} are : {}", minutes, stockSymbol, tradesInLastMinutes);
    log.debug("Entering into StockServiceImpl::getlastMinutesTrades()");
    return tradesInLastMinutes;
  }

  /**
   * This method will save the trade in the database
   *
   * @param tradeRequest contains the trade information{Stock, quantity, price, trading type}
   */
  @Override
  public void saveTrade(StockTradeRequest tradeRequest) {
    log.debug("Entering into StockServiceImpl::saveTrade() StockTradeRequest: ", tradeRequest);
    getStock(tradeRequest.getSymbol());

    Trade trade =
        new Trade.TradeBuilder()
            .symbol(tradeRequest.getSymbol())
            .type(tradeRequest.getType())
            .tradedPrice(tradeRequest.getTradePrice())
            .sharesQuantity(tradeRequest.getSharesQuantity())
            .timestamp(new Date())
            .build();

    stockRepository.create(tradeRequest.getSymbol(), trade);
    log.debug("Trade Successfully added");
    log.debug("Entering into StockServiceImpl::StockTradeRequest()");
  }

  /**
   * This method will calculate the GBCE for all the Share index
   *
   * @return calculated GBCE value
   */
  @Override
  public Double calculateGBCE() {
    log.debug("Getting GBCE");
    log.debug("Entering into StockServiceImpl::calculateGBCE()");
    List<List<Trade>> allTrades = new ArrayList<>(stockRepository.getAllTrades().values());

    List<Trade> trades =
        allTrades.stream().flatMap(trade -> trade.stream()).collect(Collectors.toList());

    Double gbce = 0d;

    if (trades != null && !trades.isEmpty()) {
      Double totalTradePrice = 1d;
      for (Trade trade : trades) {
        totalTradePrice *= trade.getTradedPrice();
      }
      Double noOfTrades = (double) trades.size();

      gbce = Math.pow(totalTradePrice, 1d / noOfTrades);
    }

    log.debug("The GBCE [{}]", gbce);
    log.debug("Entering into StockServiceImpl::calculateGBCE()");
    return gbce;
  }
}
