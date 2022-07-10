package com.jpmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.market.database.TradeMarketData;
import com.jpmc.market.domain.StockTradeRequest;
import com.jpmc.market.entity.TradeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TradeControllerTest {
  private static ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private MockMvc mvc;

  @Autowired
  private TradeMarketData tradeMarketData;

  @Test
  public void testCalculateGBCE() throws Exception {
    tradeMarketData.getTradeMarketMap().clear();
    postTrade("JOE", 50l, 250.0);

    postTrade("GIN", 50l, 150.0);
    postTrade("ALE", 45l, 165.0);

    postTrade("ALE", 27l, 222.0);

    mvc.perform(MockMvcRequestBuilders.get("/stockmarket/gbce").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gbce").value(equalTo(192.5160962148815)));
  }

  @Test
  public void testSaveTrade_WithInvalidSymbol() throws Exception {
    StockTradeRequest request = getStockTradeRequest(45l, "XXX", 852.56, TradeType.Sell);

    mvc.perform(
            post("/stockmarket/trade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value(equalTo("Stock [XXX] is invalid")));
  }

  @Test
  public void testSaveTrade() throws Exception {
    StockTradeRequest request = getStockTradeRequest(10l, "POP", 10.0, TradeType.Buy);


    mvc.perform(
            post("/stockmarket/trade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  private void postTrade(String symbol, Long shareQuantity, Double tradePrice) throws Exception{

    StockTradeRequest request = getStockTradeRequest(shareQuantity, symbol, tradePrice, TradeType.Buy);

    mvc.perform(post("/stockmarket/trade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request))
            );
  }

  private StockTradeRequest getStockTradeRequest(long sharesQuantity, String POP, double tradePrice, TradeType buy) {
    StockTradeRequest request = new StockTradeRequest();
    request.setSharesQuantity(sharesQuantity);
    request.setSymbol(POP);
    request.setTradePrice(tradePrice);
    request.setType(buy);
    return request;
  }
}
