package com.jpmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class StockControllerTest {
	private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCalculateDividendYield() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/POP/dividendyield?stockPrice=2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dividendYield").value(equalTo(4.0)))
                .andExpect(jsonPath("$.symbol").value(equalTo("POP")));
    }

    @Test
    public void testCalculateDividendYield_WithInvalidSymbol() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/XXX/dividendyield?stockPrice=10.0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(equalTo("Stock [XXX] is invalid")));
    }
    
    @Test
    public void testCalculateDividendYield_WithMissingPrice() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/POP/dividendyield").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details").value(equalTo("Required request parameter 'stockPrice' for method parameter type Double is not present")));
    }

    @Test
    public void testCalculateDividendYield_WithIncorrectPrice() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/XXX/dividendyield?stockPrice=-55.0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(equalTo("stockPrice should be positive")));
    }

    @Test
    public void testCalculatePERatio() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/POP/peratio?stockPrice=2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.peRatio").value(equalTo(0.5)))
                .andExpect(jsonPath("$.symbol").value(equalTo("POP")));
    }

    @Test
    public void testCalculatePERatio_WithInvalidSymbol() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/XXX/peratio?stockPrice=10.0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(equalTo("Stock [XXX] is invalid")));
    }
    
    @Test
    public void testCalculatePERatio_WithMissingPrice() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/POP/peratio").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details").value(equalTo("Required request parameter 'stockPrice' for method parameter type Double is not present")));

    }

    @Test
    public void testCalculatePERatio_WithIncorrectPrice() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/XXX/peratio?stockPrice=-55.0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(equalTo("stockPrice should be positive")));
    }

    @Test
    public void testCalculateVolumeWeightedPrice() throws Exception {
        postTrade("POP", 10l, 10.0);

        postTrade("POP", 20l, 20.0);

        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/POP/vwprice").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.volWeighedPrice").value(equalTo(16.666666666666668)))
                .andExpect(jsonPath("$.symbol").value(equalTo("POP")));
    }
    
    @Test
    public void testCalculateVolumeWeightedPrice_WithInvalidSymbol() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/stockmarket/stock/XXX/vwprice").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(equalTo("Stock [XXX] is invalid")));
    }

    private void postTrade(String symbol, Long shareQuantity, Double tradePrice) throws Exception{
    	
        StockTradeRequest request = new StockTradeRequest();
        request.setSharesQuantity(shareQuantity);
        request.setSymbol(symbol);
        request.setTradePrice(tradePrice);
        request.setType(TradeType.Buy);
        
     	mvc.perform(
                 post("/stockmarket/trade")
                 .contentType(MediaType.APPLICATION_JSON)
     			.content(mapper.writeValueAsString(request))
             )
                 .andExpect(status().isCreated());
    }
}
