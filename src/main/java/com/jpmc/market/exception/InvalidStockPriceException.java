package com.jpmc.market.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStockPriceException extends RuntimeException {
  public InvalidStockPriceException(String message) {
    super(message);
  }
}
