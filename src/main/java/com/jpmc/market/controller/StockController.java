package com.jpmc.market.controller;

import com.jpmc.market.domain.DividendYieldResponse;
import com.jpmc.market.domain.PERatioResponse;
import com.jpmc.market.domain.VolWeightedResponse;
import com.jpmc.market.exception.InvalidStockPriceException;
import com.jpmc.market.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockmarket")
public class StockController {

  @Autowired private StockService stockService;

  /**
   * This operation will calculate the dividend yield for given Stock and price based on the stock type
   *
   * @param stockPrice- price of the given stock
   * @param symbol- symbol of the stock
   * @return divident Yield
   */
  @GetMapping(value = "/stock/{symbol}/dividendyield")
  public DividendYieldResponse calculateDividentYield(
      @RequestParam(required = true) Double stockPrice,
      @PathVariable(required = true, name = "symbol") String symbol) {
    if (stockPrice <= 0) {
      throw new InvalidStockPriceException("stockPrice should be positive");
    }
    Double dividendYield = stockService.calculateDividendYield(symbol, stockPrice);
    return new DividendYieldResponse.DividendYieldResponseBuilder()
        .dividendYield(dividendYield)
        .stockPrice(stockPrice)
        .symbol(symbol)
        .build();
  }

  /**
   * This operation will calculate the PE ratio for given Stock and price
   *
   * @param stockPrice- price of the given stock
   * @param symbol- symbol of the stock
   * @return PE ratio for given Stock
   */
  @GetMapping(value = "/stock/{symbol}/peratio")
  public PERatioResponse calculatePERatio(
      @RequestParam(required = true) Double stockPrice, @PathVariable("symbol") String symbol) {
    if (stockPrice <= 0) {
      throw new InvalidStockPriceException("stockPrice should be positive");
    }

    Double peRatio = stockService.calculatePERatio(symbol, stockPrice);
    return new PERatioResponse.PERatioResponseBuilder()
        .peRatio(peRatio)
        .stockPrice(stockPrice)
        .symbol(symbol)
        .build();
  }

  /**
   * This operation will calculate the Volume Weighted Stock Price for given Stock
   *
   * @param symbol- symbol of the stock
   * @return Volume Weighted Stock Price
   */
  @GetMapping(value = "/stock/{symbol}/vwprice")
  public VolWeightedResponse calculateVolumeWeightedPrice(@PathVariable("symbol") String symbol) {
    Double volWeightedPrice = stockService.calculateVolWeightedPice(symbol);

    return new VolWeightedResponse.VolWeightedResponseBuilder()
        .symbol(symbol)
        .volWeighedPrice(volWeightedPrice)
        .build();
  }


}
