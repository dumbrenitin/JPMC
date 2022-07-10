package com.jpmc.market.controller;

import com.jpmc.market.domain.GBCEResponse;
import com.jpmc.market.domain.StockTradeRequest;
import com.jpmc.market.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockmarket")
public class TradeController {

  @Autowired private StockService stockService;

  /**
   * This operation will record the trade in the database
   * @param tradeRequest contains the trade information{Stock, quantity, price, trading type}
   * @return 201 status code on successful transaction
   */
  @PostMapping(value = "/trade")
  public ResponseEntity<String> saveTrade(@RequestBody StockTradeRequest tradeRequest) {
    stockService.saveTrade(tradeRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body("Trade recorded successfully ");
  }

  /**
   * This operation will calculate the GBCE value for all the Share index
   * @return GBCE value
   */
  @GetMapping("/gbce")
  public GBCEResponse calculateGBCE() {
    Double gbce = stockService.calculateGBCE();
    return new GBCEResponse.GBCEResponseBuilder().gbce(gbce).build();
  }
}
